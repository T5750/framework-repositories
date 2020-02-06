package t5750.spring.aop.cglib;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.objectweb.asm.Type;

import net.sf.cglib.beans.*;
import net.sf.cglib.core.DefaultGeneratorStrategy;
import net.sf.cglib.core.KeyFactory;
import net.sf.cglib.core.Signature;
import net.sf.cglib.proxy.*;
import net.sf.cglib.reflect.*;
import net.sf.cglib.util.ParallelSorter;
import net.sf.cglib.util.StringSwitcher;
import t5750.spring.aop.cglib.domain.OtherSampleBean;
import t5750.spring.aop.cglib.domain.SampleBean;
import t5750.spring.aop.cglib.domain.SimpleMulticastBean;
import t5750.spring.aop.cglib.proxy.AuthorizationInterceptor;
import t5750.spring.aop.cglib.proxy.PersistenceServiceCallbackFilter;
import t5750.spring.aop.cglib.service.*;
import t5750.spring.aop.cglib.service.impl.*;
import t5750.spring.test.AbstractTest;

public class CglibTest extends AbstractTest {
	private final static String RESULT = "Hello T5750!";
	private final static String RESULT_AGAIN = RESULT + " again!";

	/**
	 * 4. Implementing Proxy Using cglib<br/>
	 * 4.1. Returning the Same Value
	 */
	@Test
	public void returnValue() throws Exception {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(PersonService.class);
		enhancer.setCallback((FixedValue) () -> RESULT);
		PersonService proxy = (PersonService) enhancer.create();
		String res = proxy.sayHello(null);
		assertEquals(RESULT, res);
	}

	@Test
	public void testInvocationHandler() throws Exception {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(PersonService.class);
		enhancer.setCallback(new InvocationHandler() {
			@Override
			public Object invoke(Object proxy, Method method, Object[] args)
					throws Throwable {
				if (method.getDeclaringClass() != Object.class
						&& method.getReturnType() == String.class) {
					return RESULT;
				} else {
					throw new RuntimeException("Do not know what to do.");
				}
			}
		});
		PersonService proxy = (PersonService) enhancer.create();
		assertEquals(RESULT, proxy.sayHello(null));
		// assertNotEquals(RESULT, proxy.toString());
	}

	/**
	 * 4.2. Returning Value Depending on a Method Signature
	 */
	@Test
	public void returnSignatureValue() throws Exception {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(PersonService.class);
		enhancer.setCallback((MethodInterceptor) (obj, method, args, proxy) -> {
			if (method.getDeclaringClass() != Object.class
					&& method.getReturnType() == String.class) {
				return RESULT;
			} else {
				return proxy.invokeSuper(obj, args);
			}
		});
		PersonService proxy = (PersonService) enhancer.create();
		assertEquals(RESULT, proxy.sayHello(null));
		int lengthOfName = proxy.lengthOfName("Mary");
		assertEquals(4, lengthOfName);
		assertNotEquals(RESULT, proxy.toString());
		// Does not throw an exception or result in an endless loop.
		proxy.hashCode();
	}

	/**
	 * Immutable Bean
	 */
	@Test(expected = IllegalStateException.class)
	public void testImmutableBean() throws Exception {
		SampleBean bean = new SampleBean();
		bean.setValue(RESULT);
		SampleBean immutableBean = (SampleBean) ImmutableBean.create(bean);
		assertEquals(RESULT, immutableBean.getValue());
		bean.setValue(RESULT_AGAIN);
		assertEquals(RESULT_AGAIN, immutableBean.getValue());
		immutableBean.setValue(RESULT); // Causes exception.
	}

	/**
	 * 5. Bean Creator
	 */
	@Test
	public void beanCreator() throws Exception {
		BeanGenerator beanGenerator = new BeanGenerator();
		beanGenerator.addProperty("name", String.class);
		Object myBean = beanGenerator.create();
		Method setter = myBean.getClass().getMethod("setName", String.class);
		setter.invoke(myBean, "some string value set by a cglib");
		Method getter = myBean.getClass().getMethod("getName");
		assertEquals("some string value set by a cglib", getter.invoke(myBean));
	}

	/**
	 * Bean Copier
	 */
	@Test
	public void testBeanCopier() throws Exception {
		BeanCopier copier = BeanCopier.create(SampleBean.class,
				OtherSampleBean.class, false);
		SampleBean bean = new SampleBean();
		bean.setValue(RESULT);
		OtherSampleBean otherBean = new OtherSampleBean();
		copier.copy(bean, otherBean, null);
		assertEquals(RESULT, otherBean.getValue());
	}

	/**
	 * Bulk Bean
	 */
	@Test
	public void testBulkBean() throws Exception {
		BulkBean bulkBean = BulkBean.create(SampleBean.class,
				new String[] { "getValue" }, new String[] { "setValue" },
				new Class[] { String.class });
		SampleBean bean = new SampleBean();
		bean.setValue(RESULT);
		assertEquals(1, bulkBean.getPropertyValues(bean).length);
		assertEquals(RESULT, bulkBean.getPropertyValues(bean)[0]);
		bulkBean.setPropertyValues(bean, new Object[] { RESULT_AGAIN });
		assertEquals(RESULT_AGAIN, bean.getValue());
	}

	/**
	 * Bean Map
	 */
	@Test
	public void testBeanGenerator() throws Exception {
		SampleBean bean = new SampleBean();
		BeanMap map = BeanMap.create(bean);
		bean.setValue(RESULT);
		assertEquals(RESULT, map.get("value"));
	}

	/**
	 * Key Factory
	 */
	@Test
	public void testKeyFactory() throws Exception {
		SampleKeyFactory keyFactory = (SampleKeyFactory) KeyFactory
				.create(SampleKeyFactory.class);
		Object key = keyFactory.newInstance("foo", 42);
		Map<Object, String> map = new HashMap<Object, String>();
		map.put(key, RESULT);
		assertEquals(RESULT, map.get(keyFactory.newInstance("foo", 42)));
	}

	/**
	 * 6. Creating Mixin
	 */
	@Test
	public void createMixin() throws Exception {
		Mixin mixin = Mixin.create(
				new Class[] { MixinFirst.class, MixinSecond.class,
						MixinInterface.class },
				new Object[] { new MixinFirstImpl(), new MixinSecondImpl() });
		MixinInterface mixinDelegate = (MixinInterface) mixin;
		assertEquals("first behaviour", mixinDelegate.first());
		assertEquals("second behaviour", mixinDelegate.second());
	}

	/**
	 * String Switcher
	 */
	@Test
	public void testStringSwitcher() throws Exception {
		String[] strings = new String[] { "one", "two" };
		int[] values = new int[] { 10, 20 };
		StringSwitcher stringSwitcher = StringSwitcher.create(strings, values,
				true);
		assertEquals(10, stringSwitcher.intValue("one"));
		assertEquals(20, stringSwitcher.intValue("two"));
		assertEquals(-1, stringSwitcher.intValue("three"));
	}

	/**
	 * Interface Maker
	 */
	@Test
	public void testInterfaceMaker() throws Exception {
		Signature signature = new Signature("foo", Type.DOUBLE_TYPE,
				new Type[] { Type.INT_TYPE });
		InterfaceMaker interfaceMaker = new InterfaceMaker();
		interfaceMaker.add(signature, new Type[0]);
		Class iface = interfaceMaker.create();
		assertEquals(1, iface.getMethods().length);
		assertEquals("foo", iface.getMethods()[0].getName());
		assertEquals(double.class, iface.getMethods()[0].getReturnType());
	}

	/**
	 * Method Delegate
	 */
	@Test
	public void testMethodDelegate() throws Exception {
		SampleBean bean = new SampleBean();
		bean.setValue(RESULT);
		BeanDelegate delegate = (BeanDelegate) MethodDelegate.create(bean,
				"getValue", BeanDelegate.class);
		assertEquals(RESULT, delegate.getValueFromDelegate());
	}

	/**
	 * Multicast Delegate
	 */
	@Test
	public void testMulticastDelegate() throws Exception {
		MulticastDelegate multicastDelegate = MulticastDelegate
				.create(DelegatationProvider.class);
		SimpleMulticastBean first = new SimpleMulticastBean();
		SimpleMulticastBean second = new SimpleMulticastBean();
		multicastDelegate = multicastDelegate.add(first);
		multicastDelegate = multicastDelegate.add(second);
		DelegatationProvider provider = (DelegatationProvider) multicastDelegate;
		provider.setValue(RESULT);
		assertEquals(RESULT, first.getValue());
		assertEquals(RESULT, second.getValue());
	}

	/**
	 * Constructor Delegate
	 */
	@Test
	public void testConstructorDelegate() throws Exception {
		SampleBeanConstructorDelegate constructorDelegate = (SampleBeanConstructorDelegate) ConstructorDelegate
				.create(SampleBean.class, SampleBeanConstructorDelegate.class);
		SampleBean bean = (SampleBean) constructorDelegate.newInstance();
		assertTrue(SampleBean.class.isAssignableFrom(bean.getClass()));
	}

	/**
	 * Parallel Sorter
	 */
	@Test
	public void testParallelSorter() throws Exception {
		Integer[][] value = { { 4, 3, 9, 0 }, { 2, 1, 6, 0 } };
		ParallelSorter.create(value).mergeSort(0);
		for (Integer[] row : value) {
			int former = -1;
			for (int val : row) {
				assertTrue(former < val);
				former = val;
			}
		}
	}

	/**
	 * Fast Class and Fast Members
	 */
	@Test
	public void testFastClass() throws Exception {
		FastClass fastClass = FastClass.create(OtherSampleBean.class);
		FastMethod fastMethod = fastClass
				.getMethod(OtherSampleBean.class.getMethod("getValue"));
		OtherSampleBean myBean = new OtherSampleBean();
		myBean.setValue(RESULT);
		assertEquals(RESULT, fastMethod.invoke(myBean, new Object[0]));
	}

	/**
	 * Access the generated byte[] array directly
	 */
	@Test
	public void generateArray() throws Exception {
		Enhancer e = new Enhancer();
		e.setSuperclass(PersonService.class);
		e.setCallback((FixedValue) () -> RESULT);
		e.setStrategy(new DefaultGeneratorStrategy() {
			protected byte[] transform(byte[] b) {
				return b;
			}
		});
		PersonService obj = (PersonService) e.create();
		System.out.println(obj.sayHello(null));
	}

	/**
	 * Create a proxy using NoOp callback. The target class must have a default
	 * zero-argument constructor.
	 *
	 * @param targetClass
	 *            the super class of the proxy
	 * @return a new proxy for a target class instance
	 */
	public Object createProxy(Class targetClass) {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(targetClass);
		enhancer.setCallback(NoOp.INSTANCE);
		return enhancer.create();
	}

	/**
	 * Create a simple proxy
	 */
	@Test
	public void simpleProxy() throws Exception {
		PersistenceService persistenceService = (PersistenceServiceImpl) createProxy(
				PersistenceServiceImpl.class);
		long id = 1L;
		persistenceService.save(id, "simpleProxy");
		System.out.println(persistenceService.load(id));
	}

	/**
	 * Use a MethodInterceptor
	 */
	@Test
	public void testPersistence() throws Exception {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(PersistenceServiceImpl.class);
		CallbackFilter callbackFilter = new PersistenceServiceCallbackFilter();
		enhancer.setCallbackFilter(callbackFilter);
		AuthorizationService authorizationService = new AuthorizationServiceImpl();
		Callback saveCallback = new AuthorizationInterceptor(
				authorizationService);
		Callback loadCallback = NoOp.INSTANCE;
		Callback[] callbacks = new Callback[] { saveCallback, loadCallback };
		enhancer.setCallbacks(callbacks);
		PersistenceService persistenceService = (PersistenceServiceImpl) enhancer
				.create();
		long id = 1L;
		persistenceService.save(id, "testPersistence");
		System.out.println(persistenceService.load(id));
	}

	@Test
	public void testCallbackFilter() throws Exception {
		Enhancer enhancer = new Enhancer();
		CallbackHelper callbackHelper = new CallbackHelper(PersonService.class,
				new Class[0]) {
			@Override
			protected Object getCallback(Method method) {
				if (method.getDeclaringClass() != Object.class
						&& method.getReturnType() == String.class) {
					return new FixedValue() {
						@Override
						public Object loadObject() throws Exception {
							return RESULT;
						}
					};
				} else {
					return NoOp.INSTANCE; // A singleton provided by NoOp.
				}
			}
		};
		enhancer.setSuperclass(PersonService.class);
		enhancer.setCallbackFilter(callbackHelper);
		enhancer.setCallbacks(callbackHelper.getCallbacks());
		PersonService proxy = (PersonService) enhancer.create();
		assertEquals(RESULT, proxy.sayHello(null));
		assertNotEquals(RESULT, proxy.toString());
		// Does not throw an exception or result in an endless loop.
		proxy.hashCode();
	}
}
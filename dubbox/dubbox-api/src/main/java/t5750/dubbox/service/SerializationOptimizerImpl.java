package t5750.dubbox.service;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import com.alibaba.dubbo.common.serialize.support.SerializationOptimizer;

import t5750.dubbox.service.user.User;

public class SerializationOptimizerImpl implements SerializationOptimizer {
	public Collection<Class> getSerializableClasses() {
		List<Class> classes = new LinkedList<Class>();
		classes.add(User.class);
		return classes;
	}
}

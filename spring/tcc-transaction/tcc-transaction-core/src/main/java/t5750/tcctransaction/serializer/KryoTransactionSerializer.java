package t5750.tcctransaction.serializer;

import t5750.tcctransaction.InvocationContext;
import t5750.tcctransaction.Participant;
import t5750.tcctransaction.Terminator;
import t5750.tcctransaction.Transaction;
import t5750.tcctransaction.api.TransactionStatus;
import t5750.tcctransaction.api.TransactionXid;
import t5750.tcctransaction.common.TransactionType;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

/**
 */
public class KryoTransactionSerializer implements ObjectSerializer<Transaction> {
	private static Kryo kryo = null;
	static {
		kryo = new Kryo();
		kryo.register(Transaction.class);
		kryo.register(TransactionXid.class);
		kryo.register(TransactionStatus.class);
		kryo.register(TransactionType.class);
		kryo.register(Participant.class);
		kryo.register(Terminator.class);
		kryo.register(InvocationContext.class);
	}

	@Override
	public byte[] serialize(Transaction transaction) {
		Output output = new Output(256, -1);
		kryo.writeObject(output, transaction);
		return output.toBytes();
	}

	@Override
	public Transaction deserialize(byte[] bytes) {
		Input input = new Input(bytes);
		Transaction transaction = kryo.readObject(input, Transaction.class);
		return transaction;
	}
}

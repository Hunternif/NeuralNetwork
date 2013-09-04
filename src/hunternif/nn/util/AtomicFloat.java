package hunternif.nn.util;

import static java.lang.Float.floatToIntBits;
import static java.lang.Float.intBitsToFloat;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicFloat extends Number {

	private static final long serialVersionUID = -2739927412813425894L;

	private AtomicInteger bits;

	public AtomicFloat() {
		this(0f);
	}

	public AtomicFloat(float initialValue) {
		bits = new AtomicInteger(floatToIntBits(initialValue));
	}

	public final boolean compareAndSet(float expect, float update) {
		return bits.compareAndSet(floatToIntBits(expect),
								  floatToIntBits(update));
	}

	public final void set(float newValue) {
		bits.set(floatToIntBits(newValue));
	}

	public final float get() {
		return intBitsToFloat(bits.get());
	}

	public float floatValue() {
		return get();
	}

	public final float getAndSet(float newValue) {
		return intBitsToFloat(bits.getAndSet(floatToIntBits(newValue)));
	}

	public final boolean weakCompareAndSet(float expect, float update) {
		return bits.weakCompareAndSet(floatToIntBits(expect),
									  floatToIntBits(update));
	}

	public double doubleValue()	{ return (double) floatValue();	}
	public int intValue()		{ return (int) get();			}
	public long longValue()		{ return (long) get();			}
	
	@Override
	public String toString() {
		return String.valueOf(floatValue());
	}

}
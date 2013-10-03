package hunternif.nn.util;

import hunternif.nn.LayerIterator;
import hunternif.nn.NNException;
import hunternif.nn.NNetwork;
import hunternif.nn.Neuron;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

public class IOUtil {
	public static void serializeWeights(NNetwork network, File file) {
		try {
			JsonWriter writer = new JsonWriter(new FileWriter(file));
			writer.beginArray();
			LayerIterator iter = new LayerIterator(network);
			List<? extends Neuron> curLayer = null;
			List<? extends Neuron> nextLayer = null;
			while (true) {
				if (iter.hasNext() && nextLayer == null) {
					nextLayer = iter.next();
				}
				if (iter.hasNext()) {
					curLayer = nextLayer;
					nextLayer = iter.next();
				} else {
					break;
				}
				writer.beginArray();
				for (Neuron outNeuron : nextLayer) {
					writer.beginArray();
					for (Neuron inNeuron : curLayer) {
						writer.value(outNeuron.getInputWeight(inNeuron));
					}
					writer.endArray();
				}
				writer.endArray();
			}
			writer.endArray();
			writer.close();
		} catch (IOException e) {
			System.out.println("Failed to write to file " + file + ": " + e.toString());
		}
	}
	
	public static void deserializeWeights(NNetwork network, File file) throws NNException {
		try {
			JsonReader reader = new JsonReader(new FileReader(file));
			reader.beginArray();
			LayerIterator iter = new LayerIterator(network);
			List<? extends Neuron> curLayer = null;
			List<? extends Neuron> nextLayer = null;
			while (true) {
				if (iter.hasNext() && nextLayer == null) {
					nextLayer = iter.next();
				}
				if (iter.hasNext()) {
					curLayer = nextLayer;
					nextLayer = iter.next();
				} else {
					break;
				}
				reader.beginArray();
				for (Neuron outNeuron : nextLayer) {
					reader.beginArray();
					for (Neuron inNeuron : curLayer) {
						outNeuron.setInputWeight(inNeuron, reader.nextDouble());
					}
					reader.endArray();
				}
				reader.endArray();
			}
			reader.endArray();
			reader.close();
		} catch (IOException e) {
			System.out.println("Failed to read to file " + file + ": " + e.toString());
		} catch (IllegalStateException | NumberFormatException e) {
			throw new NNException("Reading " + file, e);
		}
	}
}

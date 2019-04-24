package anakthsh;

import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

import org.apache.lucene.search.suggest.*;
import org.apache.lucene.util.BytesRef;

public class WordIterator implements InputIterator{

	@Override
	public BytesRef next() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<BytesRef> contexts() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasContexts() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean hasPayloads() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public BytesRef payload() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long weight() {
		// TODO Auto-generated method stub
		return 0;
	}
	
}

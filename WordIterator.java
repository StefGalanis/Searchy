package anakthsh;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.lucene.search.suggest.*;
import org.apache.lucene.util.BytesRef;

public class WordIterator implements InputIterator{
	
	private Iterator<Word> wordIterator;
	private Word currentWord;

	@Override
	public Set<BytesRef> contexts() {
        try {
            Set<BytesRef> regions = new HashSet();
            //for (String region : currentWord.word) {
            regions.add(new BytesRef(currentWord.region.getBytes("UTF8")));
            //}
            return regions;
        } catch (UnsupportedEncodingException e) {
            throw new Error("Couldn't convert to UTF-8");
        }
    }

	@Override
	public boolean hasContexts() {
		return true;
	}

	@Override
	public boolean hasPayloads() {
		return true;
	}
	public Comparator<BytesRef> getComparator() {
	        return null;
	}
	
	@Override
	public BytesRef next() {
        if (wordIterator.hasNext()) {
        	currentWord = wordIterator.next();
            try {
                return new BytesRef(currentWord.word.getBytes("UTF8"));
            } catch (UnsupportedEncodingException e) {
                throw new Error("Couldn't convert to UTF-8");
            }
        } else {
            return null;
        }
    }

	@Override
	public BytesRef payload() {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(bos);
            out.writeObject(currentWord);
            out.close();
            return new BytesRef(bos.toByteArray());
        } catch (IOException e) {
            throw new Error("Well that's unfortunate.");
        }
    }

	@Override
	public long weight() {
		return currentWord.rate;
	}
	
}

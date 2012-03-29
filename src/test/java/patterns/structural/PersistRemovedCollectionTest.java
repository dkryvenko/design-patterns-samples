package patterns.structural;

import static org.mockito.Mockito.*;

import java.io.IOException;
import java.io.Writer;
import java.util.Collection;

import static org.junit.Assert.*;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

public class PersistRemovedCollectionTest {
	
	private Collection mockCollection = mock(Collection.class);
	private Writer mockWriter = mock(Writer.class);
	
	private PersistRemovedCollection collection = new PersistRemovedCollection(mockCollection, mockWriter);
		
	@Test
	public void testRemove() throws IOException {
		ArgumentCaptor<Object> obj = ArgumentCaptor.forClass(Object.class);
		ArgumentCaptor<String> str = ArgumentCaptor.forClass(String.class);
		
		collection.remove("message");
		
		verify(mockCollection).remove(obj.capture());
		verify(mockWriter).write(str.capture());
		verify(mockWriter).flush();
		assertEquals("message", obj.getValue());
		assertEquals("message", str.getValue());
	}

}

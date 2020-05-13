package edu.stanford.bmir.protege.web.shared.frame;

import edu.stanford.bmir.protege.web.shared.match.JsonSerializationTestUtil;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static edu.stanford.bmir.protege.web.MockingUtils.mockOWLClass;
import static edu.stanford.bmir.protege.web.MockingUtils.mockOWLObjectProperty;

public class PropertyClassValue_Serialization_TestCase {

    private PlainPropertyClassValue propertyValue;

    @Before
    public void setUp() throws Exception {
        propertyValue = PlainPropertyClassValue.get(mockOWLObjectProperty(),
                                               mockOWLClass());
    }

    @Test
    public void shouldSerializeAndDeserializePropertyValue() throws IOException {
        JsonSerializationTestUtil.testSerialization(propertyValue, PlainPropertyValue.class);
    }
}

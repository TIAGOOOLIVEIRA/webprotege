package edu.stanford.bmir.protege.web.server.change;

import edu.stanford.bmir.protege.web.server.util.IriReplacer;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.semanticweb.owlapi.change.AddOntologyAnnotationData;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLOntologyID;

import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

/**
 * Matthew Horridge
 * Stanford Center for Biomedical Informatics Research
 * 2019-08-28
 */
@RunWith(MockitoJUnitRunner.class)
public class AddOntologyAnnotationChange_TestCase<R> {

    private AddOntologyAnnotationChange change;

    @Mock
    private OWLOntologyID ontologyId;

    @Mock
    private OWLAnnotation ontologyAnnotation;

    @Mock
    private Set<OWLEntity> signature = Collections.singleton(mock(OWLEntity.class));

    @Mock
    private IriReplacer iriReplacer;

    @Mock
    private OntologyChangeVisitor visitor;

    @Mock
    private OntologyChangeVisitorEx<R> changeVisitorEx;

    @Before
    public void setUp() {
        change = AddOntologyAnnotationChange.of(ontologyId, ontologyAnnotation);
        when(ontologyAnnotation.getSignature())
               .thenReturn(signature);
        when(iriReplacer.replaceIris(ontologyAnnotation))
               .thenReturn(ontologyAnnotation);
    }

    @Test
    public void shouldGetSuppliedOntologyId() {
        assertThat(change.getOntologyId(), is(ontologyId));
    }

    @Test
    public void shouldGetSuppliedAnnotation() {
        assertThat(change.getAnnotation(), is(ontologyAnnotation));
    }

    @Test
    public void shouldGetSignature() {
        assertThat(change.getSignature(), is(signature));
    }

    @Test
    public void shouldReturnIsAxiomChange() {
        assertThat(change.isAxiomChange(), is(false));
    }

    @Test
    public void shouldReturnIsAddAxiom() {
        assertThat(change.isAddAxiom(), is(false));
    }

    @Test
    public void shouldReturnIsRemoveAxiom() {
        assertThat(change.isRemoveAxiom(), is(false));
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldGetAxiomOrThrow() {
        change.getAxiomOrThrow();
    }

    @Test
    public void shouldReturnIsNotChangeForAxiomType() {
        AxiomType.AXIOM_TYPES.forEach(axiomType -> assertThat(change.isChangeFor(axiomType), is(false)));

    }

    @Test
    public void shouldReplaceIris() {
        assertThat(change.replaceIris(iriReplacer), is(change));
    }

    @Test
    public void shouldVisitChange() {
        change.accept(visitor);
        Mockito.verify(visitor, Mockito.times(1)).visit(change);
    }

    @Test
    public void shouldVisitChangeEx() {
        change.accept(changeVisitorEx);
        Mockito.verify(changeVisitorEx, Mockito.times(1)).visit(change);
    }

    @Test
    public void shouldReturnIsRemoveOntologyAnnotation() {
        assertThat(change.isRemoveOntologyAnnotation(), is(false));
    }

    @Test
    public void shouldReturnIsAddOntologyAnnotation() {
        assertThat(change.isAddOntologyAnnotation(), is(true));
    }

    @Test
    public void shouldCreateOwlOntologyChangeRecord() {
        var changeRecord = change.toOwlOntologyChangeRecord();
        assertThat(changeRecord.getOntologyID(), is(ontologyId));
        assertThat(changeRecord.getData(), is(new AddOntologyAnnotationData(ontologyAnnotation)));
    }

    @Test
    public void shouldGetAnnotationOrThrow() {
        assertThat(change.getAnnotationOrThrow(), is(ontologyAnnotation));
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldGetImportsDeclarationOrThrow() {
        change.getImportsDeclarationOrThrow();
    }

    @Test
    public void shouldGetRevertingChange() {
        var revertingChange = change.getInverseChange();
        assertThat(revertingChange, is(Matchers.instanceOf(RemoveOntologyAnnotationChange.class)));
        assertThat(revertingChange.getOntologyId(), is(ontologyId));
        assertThat(revertingChange.getAnnotation(), is(ontologyAnnotation));
    }

    @Test
    public void shouldReplaceOntologyId() {
        var otherOntologyId = mock(OWLOntologyID.class);
        var replaced = change.replaceOntologyId(otherOntologyId);
        assertThat(replaced.getOntologyId(), is(otherOntologyId));
    }

    @Test
    public void shouldReturnSameForSameOntologyId() {
        var replaced = change.replaceOntologyId(ontologyId);
        assertThat(replaced, is(Matchers.sameInstance(change)));
    }
}

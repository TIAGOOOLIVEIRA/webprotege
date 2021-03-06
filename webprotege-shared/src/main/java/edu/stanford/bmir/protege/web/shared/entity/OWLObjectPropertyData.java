package edu.stanford.bmir.protege.web.shared.entity;

import com.google.auto.value.AutoValue;
import com.google.common.annotations.GwtCompatible;
import com.google.common.collect.ImmutableMap;
import edu.stanford.bmir.protege.web.shared.PrimitiveType;
import edu.stanford.bmir.protege.web.shared.shortform.DictionaryLanguage;
import org.semanticweb.owlapi.model.OWLEntityVisitorEx;
import org.semanticweb.owlapi.model.OWLObjectProperty;

import javax.annotation.Nonnull;

/**
 * Author: Matthew Horridge<br>
 * Stanford University<br>
 * Bio-Medical Informatics Research Group<br>
 * Date: 28/11/2012
 */
@AutoValue
@GwtCompatible(serializable = true)
public abstract class OWLObjectPropertyData extends OWLPropertyData {


    public static OWLObjectPropertyData get(@Nonnull OWLObjectProperty property,
                                            @Nonnull ImmutableMap<DictionaryLanguage, String> shortForms) {
        return new AutoValue_OWLObjectPropertyData(shortForms, property);
    }

    @Nonnull
    @Override
    public abstract OWLObjectProperty getObject();

    @Override
    public PrimitiveType getType() {
        return PrimitiveType.OBJECT_PROPERTY;
    }

    @Override
    public OWLObjectProperty getEntity() {
        return getObject();
    }

    @Override
    public boolean isOWLAnnotationProperty() {
        return false;
    }

    @Override
    public <R, E extends Throwable> R accept(OWLPrimitiveDataVisitor<R, E> visitor) throws E {
        return visitor.visit(this);
    }

    @Override
    public <R> R accept(OWLEntityVisitorEx<R> visitor, R defaultValue) {
        return visitor.visit(getEntity());
    }

    @Override
    public <R> R accept(OWLEntityDataVisitorEx<R> visitor) {
        return visitor.visit(this);
    }
}

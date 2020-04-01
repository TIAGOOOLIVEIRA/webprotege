package edu.stanford.bmir.protege.web.shared.frame;

import com.google.auto.value.AutoValue;
import com.google.common.annotations.GwtCompatible;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationValue;

import javax.annotation.Nonnull;

/**
 * Matthew Horridge
 * Stanford Center for Biomedical Informatics Research
 * 2020-03-31
 */
@AutoValue
@GwtCompatible(serializable = true)
public abstract class PlainPropertyAnnotationValue extends PlainPropertyValue {

    public static PlainPropertyAnnotationValue get(@Nonnull OWLAnnotationProperty property,
                                                   @Nonnull OWLAnnotationValue annotationValue,
                                                   @Nonnull State state) {
        return new AutoValue_PlainPropertyAnnotationValue(property,
                                                          annotationValue,
                                                          state);
    }

    @Nonnull
    @Override
    public abstract OWLAnnotationProperty getProperty();

    @Nonnull
    @Override
    public abstract OWLAnnotationValue getValue();

    @Nonnull
    @Override
    public abstract State getState();

    @Override
    public <R> R accept(PlainPropertyValueVisitor<R> visitor) {
        return visitor.visit(this);
    }

    @Nonnull
    @Override
    protected PlainPropertyAnnotationValue withState(State state) {
        return get(getProperty(), getValue(), state);
    }

    @Nonnull
    @Override
    public PropertyAnnotationValue toPropertyValue(@Nonnull PrimitiveRenderer renderer) {
        return PropertyAnnotationValue.get(
                renderer.getRendering(getProperty()),
                renderer.getRendering(getValue()),
                getState()
        );
    }
}

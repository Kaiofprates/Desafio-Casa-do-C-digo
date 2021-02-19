package br.com.zup.desafio1.validate.StateExists;

import br.com.zup.desafio1.handler.exceptions.StateDuplicateException;
import org.springframework.beans.BeanWrapperImpl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

public class ExistsStateValidator implements ConstraintValidator<ExistsState, Object> {

    private List<String> fields;
    private Class<?> klass;

    @PersistenceContext
    private EntityManager manager;

    @Override
    public void initialize(ExistsState params) {
        fields = Arrays.asList(params.fieldName());
        klass = params.domainClass();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        Query query = manager.createQuery("select s from State s where s.country = :countryId and s.name =:name");
        query.setParameter(fields.get(1), new BeanWrapperImpl(value).getPropertyValue(fields.get(1)));
        query.setParameter(fields.get(0), new BeanWrapperImpl(value).getPropertyValue(fields.get(0)));
        List<?> list = query.getResultList();
        if (list.isEmpty()) {
            throw new StateDuplicateException("The informed state does not exist in that country");
        }

        return true;
    }
}
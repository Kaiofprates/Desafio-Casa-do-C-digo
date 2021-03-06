package br.com.zup.desafio1.State.form;

import br.com.zup.desafio1.Country.Country;
import br.com.zup.desafio1.State.State;
import br.com.zup.desafio1.validate.IdExists.ExistId;
import br.com.zup.desafio1.validate.StateDuplicate.StateForCountryValue;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
@StateForCountryValue(domainClass = State.class,fieldName = { "name", "countryId" })
public class StateRequest {
    @NotBlank
    private String name;

    @NotNull
    @ExistId(domainClass = Country.class,fieldName = "id")
    private Long countryId;

    public StateRequest(String name, Long coutryId) {
        this.name = name;
        this.countryId = coutryId;
    }

    public String getName() {
        return name;
    }

    public Long getCountryId() {
        return countryId;
    }

    @Transactional
    public State toModel(EntityManager manager) {
      @NotNull Country country = manager.find(Country.class,this.countryId);
      Assert.state(country != null, "Country not found");
      State state = new State(this.name,country.getId(),country.getName());
      return state;
    }
}

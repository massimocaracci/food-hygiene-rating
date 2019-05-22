package uk.co.pantasoft.fhr.client.establishments;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class FSAEstablishments {

    @JsonProperty("establishments")
    private List<FSAEstablishmentItem> establishments;

    public List<FSAEstablishmentItem> getEstablishments() {
        return establishments;
    }

    public void setEstablishments(List<FSAEstablishmentItem> establishments) {
        this.establishments = establishments;
    }
}

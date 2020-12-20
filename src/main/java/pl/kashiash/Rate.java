
package pl.kashiash;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "currency",
        "code",
        "ask",
        "mid",
        "bid"

})
public class Rate {
    public Rate(String currency, String code, Double mid, Double bid, Double ask) {
        this.currency = currency;
        this.code = code;
        this.mid = mid;
        this.bid = bid;
        this.ask = ask;
    }

    public Rate() {
    }

    @JsonProperty("currency")
    private String currency;
    @JsonProperty("code")
    private String code;
    @JsonProperty("mid")
    private Double mid;
    @JsonProperty("bid")
    private Double bid;
    @JsonProperty("ask")
    private Double ask;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("currency")
    public String getCurrency() {
        return currency;
    }

    @JsonProperty("currency")
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @JsonProperty("code")
    public String getCode() {
        return code;
    }

    @JsonProperty("code")
    public void setCode(String code) {
        this.code = code;
    }

    @JsonProperty("mid")
    public Double getMid() {
        return mid;
    }

    @JsonProperty("mid")
    public void setMid(Double mid) {
        this.mid = mid;
    }

    @JsonProperty("bid")
    public Double getBid() {
        return bid;
    }

    @JsonProperty("bid")
    public void setBid(Double bid) {
        this.bid = bid;
    }

    @JsonProperty("ask")
    public Double getAsk() {
        return ask;
    }

    @JsonProperty("ask")
    public void setAsk(Double ask) {
        this.ask = ask;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}

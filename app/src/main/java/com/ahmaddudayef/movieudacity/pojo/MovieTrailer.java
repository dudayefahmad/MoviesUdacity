
package com.ahmaddudayef.movieudacity.pojo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MovieTrailer {

    private Integer id;
    private List<Trailer> results = null;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 
     * @return
     *     The trailer
     */
    public List<Trailer> getTrailer() {
        return results;
    }

    /**
     * 
     * @param trailer
     *     The trailer
     */
    public void setTrailer(List<Trailer> trailer) {
        this.results = trailer;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}

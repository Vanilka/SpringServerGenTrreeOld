package com.genealogytree.client.desktop.service.responses;

import com.genealogytree.client.desktop.domain.GTX_Family;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by vanilka on 11/11/2016.
 */
@Setter
@Getter
public class ListFamilyResponse extends ServiceResponse {

    List<GTX_Family> listFamily;

    public ListFamilyResponse() {
        this(null);
    }

    public ListFamilyResponse(List<GTX_Family> list) {
        super(ResponseStatus.OK);
        this.listFamily = list;
    }

    @Override
    public String toString() {
        return "ListFamilyResponse{" +
                "listFamily=" + listFamily +
                "} " + super.toString();
    }
}

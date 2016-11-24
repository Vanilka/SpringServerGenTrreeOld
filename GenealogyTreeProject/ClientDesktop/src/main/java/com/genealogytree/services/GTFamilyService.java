package com.genealogytree.services;

import com.genealogytree.application.GenealogyTreeContext;
import com.genealogytree.domain.GTX_Family;
import com.genealogytree.services.responses.ServerResponse;

/**
 * Created by vanilka on 22/11/2016.
 */
public interface GTFamilyService {

     void setContext(GenealogyTreeContext context);
     ServerResponse getProjects();
     ServerResponse addNewProject(GTX_Family familyBean);

      void setCurrentFamily(GTX_Family family);
     GTX_Family getCurrentFamily();

}

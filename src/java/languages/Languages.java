/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package languages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 *
 * @author brettfry
 */
@Named("languages")
@RequestScoped
public class Languages {

    List<String> list = new ArrayList<String>();

    //create list for langauge dropdown.
    public List<String> getItems() {

        list.add("en");
        list.add("de");
        list.add("ru");
        return list;
    }

    Map reference = new HashMap();
    Map<String, String> language = new LinkedHashMap<String, String>();

    public Map<String, String> getMap() {
        language.put("en", "English");
        language.put("de", "German");
        language.put("ru", "Russian");
        reference.put("languageList", language);
        //return language;
        return reference;
    }

}

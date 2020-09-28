
package com.sdigitizers.notification.pushnotf;

import com.currencyfair.onesignal.model.notification.Button;
import com.currencyfair.onesignal.model.notification.Filter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Shriram Prajapat
 */
public class PushNotfMessage {
    
    private List<Button> buttons;
    private Map<String, String> headings;
    private Map<String, String> contents;
    private Map<String, String> data;
    private Filter filter;
    private String bigPictureUrl;

    public List<Button> getButtons() {
        return buttons;
    }

//        List<Button> btns = new ArrayList<>();
//         btns.add(new Button("1", "Reply", "", "www.google.com"));
//        notf.setButtons(btns);    
    public PushNotfMessage setButtons(List<Button> buttons) {
        this.buttons = buttons;
        return this;
    }
    public PushNotfMessage setButton(Button button) {
        if(null == buttons) buttons = new ArrayList<>(1);
        this.buttons.add(button);
        return this;
    }

    public Map<String, String> getHeadings() {
        return headings;
    }

    public PushNotfMessage setHeadings(Map<String, String> headings) {
        this.headings = headings;
        return this;
    }
    
    /**
     * In English [en]
     * @param heading
     * @return 
     */
    public PushNotfMessage setHeading(String heading){
        if(null == headings) headings = new HashMap<>(1);
        headings.put("en", heading);
        return this;
    }

    public Map<String, String> getContents() {
        return contents;
    }

    public PushNotfMessage setContent(String content){
        if(null == contents) contents = new HashMap<>(1);
        contents.put("en", content);
        return this;
    }
    
    public PushNotfMessage setContents(Map<String, String> contents) {
        this.contents = contents;
        return this;
    }

    public Map<String, String> getData() {
        return data;
    }

    public PushNotfMessage setData(Map<String, String> data) {
        this.data = data;
        return this;
    }

    public Filter getFilter() {
        return filter;
    }

    public PushNotfMessage setFilter(Filter filter) {
        this.filter = filter;
        return this;
    }

    public String getBigPictureUrl() {
        return bigPictureUrl;
    }

    public PushNotfMessage setBigPictureUrl(String bigPictureUrl) {
        this.bigPictureUrl = bigPictureUrl;
        return this;
    }
    
}

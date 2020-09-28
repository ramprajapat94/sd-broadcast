
package com.sdigitizers.notification.sms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Shriram Prajapat
 */
public class SmsMessage {
    
    private String message;
    private List<String> numbers;
    private final SmsType type;

    public SmsMessage(SmsType type) {
        this.type = type;
    }

    public SmsType getType() {
        return type;
    }
    
    public String getMessage() {
        return message;
    }
    
    public SmsMessage setMessage(String message) {
        this.message = message;
        return this;
    }

    public List<String> getNumbers() {
        return numbers;
    }

    
    
    public SmsMessage setNumbers(List<String> numbers) {
        this.numbers = numbers;
        return this;
    }
    
    public SmsMessage setNumbers(String... numbers){
        this.numbers = Arrays.asList(numbers);
        return this;
    }
    
    public SmsMessage setNumbers(String numbers){
        if(numbers.contains(",")){
            this.numbers = getNumbersFromString(numbers);
        }else{
            this.numbers = new ArrayList<>(1);
            this.numbers.add(numbers);
        }
        return this;
    }
    
    
    public String getNumbersAsString() {
        StringBuilder numbersString = new StringBuilder("");
        for(String phone : this.numbers){
             numbersString.append(",").append(phone);
         }
         numbersString.deleteCharAt(0);
         return numbersString.toString();
    }
    
    public static List<String> getNumbersFromString(String numberString){
        String[] split = numberString.split(",");
        return Arrays.asList(split);
    }
    
}

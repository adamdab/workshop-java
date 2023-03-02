package org.workshop.utils;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StringCalculator {
  public static int sum(String str) {
   if(str.isEmpty()) return 0;
   List<String> delimiters = Stream.of(",","\n").collect(Collectors.toList());
   if(str.contains("//")) {
     String newDelimited = String.valueOf(str.charAt(2));
     delimiters.add(newDelimited);
     str = str.substring(2);
   }
   if(matches(str, delimiters.toArray(String[]::new))) {
     String[] numbers = str.split(String.join("|", delimiters));
     int result = 0;
     for (String num : numbers) {
       try {
         int number = Integer.parseInt(num);
         if (number < 0) throw new IllegalArgumentException("negative value of one of arguments");
         number = number > 1000 ? 0 : number;
         result += number;
       } catch (NumberFormatException ignored) {}

     }
     return result;
   }
   else {
     int result = Integer.parseInt(str);
     if(result < 0) throw new IllegalArgumentException("negative value of one of arguments");
     return result;
   }
  }

  private static boolean matches(String input, String[] delimiters) {
    for(String delimiter : delimiters) {
      if(input.contains(delimiter)) return true;
    }
    return false;
  }
}

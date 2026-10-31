package dsa.question2;

import java.util.ArrayList;
import java.util.List;

public class Demo {

  public static void main(String arg[]) {
      // part1
      String header = "   en-US   ,     fr-CA,     fr-FR     ";
      String[] supported_languages =  {"fr-CA", "en-UK", "fr-FR", "en-US"};

      List<String> output = LanguageParserPart1.parseLanguage(header, supported_languages);
      System.out.println(output);
  }

}

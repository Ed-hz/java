package v2ch08.rect;

import v2ch08.sourceAnnotations.ToStrings;

public class SourceLevelAnnotationDemo
{
   public static void main(String[] args)
   {
      var rect = new Rectangle(new Point(10, 10), 20, 30);
      System.out.println(ToStrings.toString(rect));
   }
}

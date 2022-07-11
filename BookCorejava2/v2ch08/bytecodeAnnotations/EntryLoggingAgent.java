package v2ch08.bytecodeAnnotations;

import java.lang.instrument.*;
import java.security.ProtectionDomain;
import org.objectweb.asm.*;

/**
 * @version 1.11 2018-05-01
 * @author Cay Horstmann
 */
public class EntryLoggingAgent
{
   public static void premain(final String arg, Instrumentation instr)
   {
      instr.addTransformer(new ClassFileTransformer() {
         @Override
         public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
             ProtectionDomain protectionDomain, byte[] classfileBuffer)
             throws IllegalClassFormatException {
           if (!className.replace("/", ".").equals(arg)) return null;
            var reader = new ClassReader(classfileBuffer);
            var writer = new ClassWriter(
               ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
            var el = new EntryLogger(writer, className);
            reader.accept(el, ClassReader.EXPAND_FRAMES);
            return writer.toByteArray();
         }
       }, true);
   }
}

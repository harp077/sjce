package SJCE.xgui;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
public class EngineIO {
 private Process process;
 private BufferedReader reader;
 private PrintWriter writer;
 private String command;
 public EngineIO(String command) {  this.command = command;  start(); }
 private void start() {
  Runtime runtime = Runtime.getRuntime();
  try {
   process = runtime.exec(command);
   reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
   writer = new PrintWriter(new OutputStreamWriter(process.getOutputStream()),true);
   runtime.addShutdownHook(new Thread() {
    @Override
    public void run() { shutdownPerformed(); }
   });
  } catch (IOException ex) {   ex.printStackTrace();  }
 }
 private void shutdownPerformed() {
  if(writer != null) {   writeLine("quit");   writer.close();  }
  process.destroy();
  //process.destroyForcibly();
//////  THIS = MY !!!!!!!!!!!!!!!  
//  if(reader != null) {   try {
//      reader.close();
//      } catch (IOException ex) {
//        Logger.getLogger(EngineIO.class.getName()).log(Level.SEVERE, null, ex);
//      }
//    }
 }
 public String readLine() throws IOException {
  String string = reader.readLine();
  if(string != null) System.out.println(string);
  return string;
 }
 public void writeLine(String string) {
  System.out.println(string);  writer.println(string);
 }
 public void restart() {  process.destroy();  start(); }
 public void destroy() {  process.destroy(); }
 public Process getProcess() {  return process; }
 public String getCommand()  {  return command; }
}

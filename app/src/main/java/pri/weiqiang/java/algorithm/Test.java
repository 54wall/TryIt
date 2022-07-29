///**
// * Copyright (c) 2022 BodyPark Co. 版权所有 请勿泄题
// * 后续面试会问相关问题，所以请勿借助他人
// * <p>
// * The input is a text stream of multiple lines. If you
// * are not familiar with the concept of i/o stream you may
// * search it on the web.
// * Your task is to find the longest continous substring
// * consists of only "valid" characters for each line.
// * Here, a character is called "valid" if it is an
// * English letter (upper or lower case) or a space.
// * The output should also be a text stream of multiple
// * lines and each line should be the longest continous
// * substring of its corresponding input line. Please
// * note that the output lines should be in the exact
// * same order as their input lines and the number of
// * input lines and output lines should be the same.
// * <p>
// * Here is an example of three lines of input text:
// * :LSu9f*&;23lk45
// * 0ue u987*6OIIe
// * 765^*^%$*^&(*354
// * And the ouput stream should also be three lines:
// * LSu
// * ue u
// * <p>
// * <p>
// * InStream and OutStream are your interfaces for input
// * and output respectively. You should use them for I/O
// * and they have only the methods defined below. Don't
// * waste your time reading their implementations, which
// * are not relevant to this interview.
// * <p>
// * TWO TASKs:
// * 1. Add enough (according to you) lines to the value of UnittestCases in ShowMebug.testLongestValidSubStrings (near the bottom of this file) as test cases.
// * 2. Implement LongestValidSubstrings() of Processor class
// * <p>
// * ADDITIONAL REQUIREMENTS:
// * Do not use regular expression.
// * Use as little memory as you can.
// * <p>
// * You may search on the web freely when you code.
// * <p>
// * If any questions please contact me.
// */
//
//import org.junit.Test;
//import org.junit.internal.TextListener;
//import org.junit.runner.JUnitCore;
//
//import java.io.BufferedReader;
//import java.io.ByteArrayInputStream;
//import java.io.IOException;
//import java.io.InputStreamReader;
//
///**
// * The interface for input stream
// *
// * Only sequential access allowed.
// */
//interface InStream {
//    /*
//     * Read a character from input stream
//     */
//    public Character readChar() throws IOException;
//
//    /*
//     * Read a line from input stream
//     */
//    public String readLine() throws IOException;
//
//    /*
//     * Read a block of text from input stream
//     * Parameters:
//     *   size: the length of text block to read
//     */
//    public String readBlock(int size) throws IOException;
//}
//
///**
// * The interface for output stream
// */
//interface OutStream {
//    /*
//     * Write a character to output stream
//     */
//    public void writeChar(char ch);
//
//    /*
//     * Write a line to output stream
//     */
//    public void writeLine(String line);
//
//    /*
//     * Write a text block to output stream
//     */
//    public void writeBlock(String block);
//}
//
//class Processor {
//    public Processor(InStream instream, OutStream outstream) {
//        this.instream = instream;
//        this.outstream = outstream;
//    }
//
//    public void LongestValidSubStrings() throws IOException {
//        // TASK 2: PLEASE IMPLEMENT THIS.
//        // Note: You may define other functions and/or classes as you wish
//        // System.out.println("LongestValidSubStrings");
//        // ByteArrayOutputStream outputStream = ByteArrayOutputStream();
//        // byte[] bytes = new byte[1024];
//        // int len;
//        // while((len = instream.read(bytes))!=-1){
//        //   outputStream.write(bytes,0,len);
//        // }
//        // instream.close();
//        // String content = outputStream.toString();
//        // outputStream.close();
//        String content = instream.readLine();
//        // System.out.println("content:"+content);
//        char[] chars = content.toCharArray();
//
//        //0ue u987*6OIIe -> ue u
//        //map存储下标和字母空格数量
//        // HashMap<Integer,Integer> map = new HashMap<>();
//        int[] arr = new int[chars.length];
//        int indexLast = 0;
//        for (int i = 0; i < chars.length; i++) {
//            if (i == 0 && isCharAndEmpty(chars[i])) {
//                // map.put(i,1);
//                arr[i] = 1;
//            } else if (i >= 1 && isCharAndEmpty(chars[i - 1]) && isCharAndEmpty(chars[i])) {
//                // map.put(indexLast,map.get(indexLast)+1);
//                arr[indexLast] = arr[indexLast] + 1;
//                // System.out.println("indexLast :"+indexLast+", arr[indexLast]:"+ arr[indexLast]);
//                // System.out.println("indexLast :"+indexLast);
//                // System.out.println("processi:"+i+" map.get("+indexLast+"):"+map.get(indexLast));
//            } else if (isCharAndEmpty(chars[i])) {
//                indexLast = i;
//                // map.put(indexLast,1);
//                arr[indexLast] = 1;
//            }
//        }
//        int indexMax = 0;
//        for (int i = 0; i < arr.length; i++) {
//            if (arr[i] != 0 && arr[i] > arr[indexMax]) {
//                indexMax = i;
//            }
//        }
//
//        String result = content.substring(indexMax, indexMax + arr[indexMax]);
//
//
//        // if(map.size()>0){
//        //   int indexMax = (Integer)map.keySet().toArray()[0];
//        // int valueMax = (Integer)map.values().toArray()[0];
//        // for(Integer key:map.keySet()){
//        //   int value = map.get(key);
//        //   if(value>valueMax){
//        //     indexMax = key.intValue();
//        //   }
//        // }
//        // System.out.println("indexMax:"+indexMax+" "+map.get(indexMax));
//        // System.out.println("content:"+content);
//        // result = content.substring(indexMax,indexMax+map.get(indexMax));
//
//        // }else{
//        //   result="";
//        // }
//        // System.out.println("result:"+result);
//        outstream.writeLine(result);
//
//    }
//
//    private boolean isCharAndEmpty(char c) {
//        if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || (c == ' ')) {
//            return true;
//        } else {
//            return false;
//        }
//    }
//
//    private InStream instream = null;
//    private OutStream outstream = null;
//};
//
///**
// * The mock implementation of InStream. Please ignore.
// */
//class MockInStream implements InStream {
//    public Character readChar() throws IOException {
//        int code = implReader.read();
//        if (code == -1)
//            return null;
//
//        return Character.valueOf((char) code);
//    }
//
//    public String readLine() throws IOException {
//        return implReader.readLine();
//    }
//
//    public String readBlock(int size) throws IOException {
//        var buffer = new char[size];
//        var count = implReader.read(buffer, 0, size);
//        if (count == -1)
//            return null;
//        else
//            return new String(buffer);
//    }
//
//    public MockInStream(String testCases) {
//        implStream = new ByteArrayInputStream(testCases.getBytes());
//        implReader = new BufferedReader(new InputStreamReader(implStream));
//    }
//
//    private ByteArrayInputStream implStream = null;
//    private BufferedReader implReader = null;
//}
//
///**
// * The mock implementation of OutStream. Please ignore.
// */
//class MockOutStream implements OutStream {
//    public void writeChar(char ch) {
//        System.out.print(ch);
//    }
//
//    public void writeLine(String line) {
//        System.out.println(line);
//    }
//
//    public void writeBlock(String block) {
//        System.out.print(block);
//    }
//}
//
//public class ShowMeBug {
//    @Test
//    public void testProcessor() throws IOException {
//        // TASK 1: PLEASE ADD INPUT LINES HERE AS TESTS.
//        //LSu9f*&;23lk45
//        // 0ue u987*6OIIe
//        // 765^*^%$*^&(*354
//        String UnittestCases = "0ue u987*6OIIe";
//
//        InStream instream = new MockInStream(UnittestCases);
//        OutStream outstream = new MockOutStream();
//        var processor = new Processor(instream, outstream);
//
//        processor.LongestValidSubStrings();
//    }
//
//    public static void main(String[] args) {
//        JUnitCore junit = new JUnitCore();
//        junit.addListener(new TextListener(System.out));
//        junit.run(ShowMeBug.class);
//    }
//}
/*
 * The MIT License
 *
 * Copyright 2015 gburdell.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package apfe.v2.vlogpp;

import static apfe.v2.vlogpp.Global.stToolRoot;
import gblib.File;
import gblib.MessageMgr;

/**
 *
 * @author gburdell
 */
public class Messages {

    public static void message(char severity, String code, Object... args) {
        MessageMgr.message(severity, code, args);
    }
    
    public static Message getMessage(char severity, String code, Object... args) {
        return new Message(severity, code, args);
    }
    
    public static void print(final Message msg) {
        MessageMgr.print(msg);
    }
    
    private static final boolean stLoaded = load();

    private static boolean load() {
        final File fname = new File(stToolRoot, "messages.txt");
        MessageMgr.addMessages(fname);
        return true;
    }
    
    public static class Message extends MessageMgr.Message {

        public Message(char severity, String code, Object... args) {
            super(severity, code, args);
        }
        
    }
}

//The MIT License
//
//Copyright (c) 2006-2010  Karl W. Pfalzer
//Copyright (c) 2011-      George P. Burdell
//
//Permission is hereby granted, free of charge, to any person obtaining a copy
//of this software and associated documentation files (the "Software"), to deal
//in the Software without restriction, including without limitation the rights
//to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
//copies of the Software, and to permit persons to whom the Software is
//furnished to do so, subject to the following conditions:
//
//The above copyright notice and this permission notice shall be included in
//all copies or substantial portions of the Software.
//
//THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
//IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
//FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
//AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
//LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
//OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
//THE SOFTWARE.
package apfe.dsl.vlogpp;
import gblib.Pair;
import apfe.dsl.vlogpp.parser.FormalArgument;
import apfe.dsl.vlogpp.parser.ListOfFormalArguments;
import  java.util.*;

/**
 * A parameter name and its default value.
 * 
 * @author kpfalzer
 */
public class Parm extends Pair<String,String> {
    public static List<Parm> createList(final ListOfFormalArguments lofas) {
        List<Parm> rval = null;
        if (null != lofas) {
            List<FormalArgument> lofa = lofas.getFormalArgs();
            if (null != lofa) {
                rval = new LinkedList<>();
                String idAndDflt[];
                for (FormalArgument fa : lofa) {
                    idAndDflt = fa.getIdAndDefaultText();
                    rval.add(new Parm(idAndDflt[0], idAndDflt[1]));
                }
            }
        }
        return rval;
    }
    public Parm(String parm) {
        super(parm,null);
    }

    public Parm(String parm, String dflt) {
        super(parm,dflt);
    }

    public void setDefault(String dflt) {
        super.v2 = dflt;
    }

    public String getParmName() {
        return v1;
    }

    public String getDefault() {
        return v2;
    }

    public boolean hasDefault() {
        return (null != getDefault());
    }
}

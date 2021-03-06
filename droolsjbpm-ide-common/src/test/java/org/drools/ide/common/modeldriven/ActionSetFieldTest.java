/*
 * Copyright 2010 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.drools.ide.common.modeldriven;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import org.drools.ide.common.client.modeldriven.SuggestionCompletionEngine;
import org.drools.ide.common.client.modeldriven.brl.ActionFieldValue;
import org.drools.ide.common.client.modeldriven.brl.ActionSetField;

public class ActionSetFieldTest {

    @Test
    public void testRemove() {
        final ActionSetField set = new ActionSetField();
        set.fieldValues = new ActionFieldValue[2];
        final ActionFieldValue v0 = new ActionFieldValue( "x",
                                                          "42",
                                                          SuggestionCompletionEngine.TYPE_NUMERIC_INTEGER );
        final ActionFieldValue v1 = new ActionFieldValue( "y",
                                                          "43",
                                                          SuggestionCompletionEngine.TYPE_NUMERIC_INTEGER );
        set.fieldValues[0] = v0;
        set.fieldValues[1] = v1;

        set.removeField( 1 );

        assertEquals( 1,
                      set.fieldValues.length );
        assertEquals( v0,
                      set.fieldValues[0] );

    }

    @Test
    public void testAdd() {
        final ActionSetField set = new ActionSetField();
        set.fieldValues = new ActionFieldValue[2];
        final ActionFieldValue v0 = new ActionFieldValue( "x",
                                                          "42",
                                                          SuggestionCompletionEngine.TYPE_NUMERIC_INTEGER );
        final ActionFieldValue v1 = new ActionFieldValue( "y",
                                                          "43",
                                                          SuggestionCompletionEngine.TYPE_NUMERIC_INTEGER );
        set.fieldValues[0] = v0;
        set.fieldValues[1] = v1;

        final ActionFieldValue q = new ActionFieldValue( "q",
                                                         "q",
                                                         SuggestionCompletionEngine.TYPE_NUMERIC_INTEGER );
        set.addFieldValue( q );

        assertEquals( 3,
                      set.fieldValues.length );
        assertEquals( q,
                      set.fieldValues[2] );
        assertEquals( v0,
                      set.fieldValues[0] );

    }

}

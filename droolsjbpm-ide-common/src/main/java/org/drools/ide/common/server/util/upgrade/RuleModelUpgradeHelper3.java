/*
 * Copyright 2011 JBoss Inc
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.drools.ide.common.server.util.upgrade;

import org.drools.ide.common.client.modeldriven.brl.CompositeFactPattern;
import org.drools.ide.common.client.modeldriven.brl.CompositeFieldConstraint;
import org.drools.ide.common.client.modeldriven.brl.ConnectiveConstraint;
import org.drools.ide.common.client.modeldriven.brl.FactPattern;
import org.drools.ide.common.client.modeldriven.brl.FieldConstraint;
import org.drools.ide.common.client.modeldriven.brl.IPattern;
import org.drools.ide.common.client.modeldriven.brl.RuleModel;
import org.drools.ide.common.client.modeldriven.brl.SingleFieldConstraint;

/**
 * Utility class to support upgrades of the RuleModel model. This implementation
 * ensures additional SingleFieldConstraint and ConnectiveConstraint fields
 * created for Guvnor v5.4 have reasonable default values.
 */
public class RuleModelUpgradeHelper3
    implements
    IUpgradeHelper<RuleModel, RuleModel> {

    public RuleModel upgrade(RuleModel model) {
        updateConstraints( model );
        return model;
    }

    //As from 5.4.0 FieldConstraints hold the FactType to which they relate. This is necessary as
    //both "nested" field constraints and ExpressionFormLine constraints didn't have knowledge of the
    //FactType to which they related and hence field completions could not be ascertained. This also
    //restores fieldNames, that were temporarily fully qualified with the factType, before this more
    //complete fix was made. See BZ724448 (Support for BigDecimal in Guided Editors)
    private void updateConstraints(RuleModel model) {
        for ( IPattern p : model.lhs ) {
            fixConstraints( p );
        }
    }

    //Descent into the model
    private void fixConstraints(IPattern p) {
        if ( p instanceof FactPattern ) {
            fixConstraints( (FactPattern) p );
        } else if ( p instanceof CompositeFactPattern ) {
            fixConstraints( (CompositeFactPattern) p );
        }
    }

    private void fixConstraints(FactPattern p) {
        for ( FieldConstraint fc : p.getFieldConstraints() ) {
            fixConstraints( p,
                            fc );
        }
    }

    private void fixConstraints(CompositeFactPattern p) {
        for ( IPattern cp : p.getPatterns() ) {
            fixConstraints( cp );
        }
    }

    private void fixConstraints(FactPattern fp,
                                FieldConstraint fc) {
        if ( fc instanceof SingleFieldConstraint ) {
            fixConstraints( fp,
                            (SingleFieldConstraint) fc );
        } else if ( fc instanceof CompositeFieldConstraint ) {
            fixConstraints( fp,
                            (CompositeFieldConstraint) fc );
        }
    }

    private void fixConstraints(FactPattern fp,
                                SingleFieldConstraint sfc) {
        sfc.setFactType( fp.getFactType() );
        sfc.setFieldName( fixFieldName( sfc.getFieldName() ) );
        if ( sfc.connectives == null ) {
            return;
        }
        for ( ConnectiveConstraint cc : sfc.connectives ) {
            cc.setFactType( fp.getFactType() );
            cc.setFieldName( fixFieldName( cc.getFieldName() ) );
        }
    }

    private void fixConstraints(FactPattern fp,
                                CompositeFieldConstraint cfc) {
        if ( cfc.constraints == null ) {
            return;
        }
        for ( FieldConstraint fc : cfc.constraints ) {
            fixConstraints( fp,
                            fc );
        }
    }

    private String fixFieldName(String fieldName) {
        if ( fieldName == null ) {
            return null;
        }
        int dotIndex = fieldName.indexOf( "." );
        if ( dotIndex != -1 ) {
            return fieldName.substring( dotIndex + 1 );
        }
        return fieldName;
    }

}

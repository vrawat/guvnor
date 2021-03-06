/*
 * Copyright 2012 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.drools.guvnor.client.rpc;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.user.client.rpc.IsSerializable;

import org.jboss.errai.common.client.api.annotations.Portable;

@Portable
public class PathImpl implements Path, IsSerializable {

    private String uri = null;
    private String fileName = null;
    private HashMap<String, Object> attributes = null;

    private String uuid;
    
    public PathImpl() {
    }

    public PathImpl(final String uri) {
        this(null, uri, null);
    }

    public PathImpl(final String fileName, final String uri) {
        this(fileName, uri, null);
    }

    public PathImpl(final String fileName, final String uri, final Map<String, Object> attrs) {
        this.fileName = fileName;
        this.uri = uri;
        if (attrs == null){
            this.attributes = new HashMap<String, Object>();
        } else {
            this.attributes = new HashMap<String, Object>(attrs);
        }
    }

    @Override
    public String getFileName() {
        return fileName;
    }

    @Override
    public String toURI() {
        return uri;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public String getUUID() {
		return uuid;
	}

	public void setUUID(String uuid) {
		this.uuid = uuid;
	}
	
	public int compareTo(org.uberfire.backend.vfs.Path another) {
		//TODO: compare using path
		if(another instanceof PathImpl) {
			PathImpl anotherPathImpl = (PathImpl)another;
			return this.getUUID().compareTo(anotherPathImpl.getUUID());
		}
		return -1;
	}

	public boolean equals(org.uberfire.backend.vfs.Path another) {
		//TODO:
		if(another instanceof PathImpl) {
			return this.getUUID().equals(((PathImpl)another).getUUID());
		}
		return false;
	}
	
	@Override
    public String toString() {
        return "PathImpl{" +
                "uuid='" + uuid + '\'' +
                ", uri='" + uri + '\'' +
                ", fileName='" + fileName + '\'' +
                ", attrs=" + attributes +
                '}';
    }
}

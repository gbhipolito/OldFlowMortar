/*
 * Copyright 2013 Square Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package mr.random.guy.oldflowmortar.support;

import android.content.Context;
import android.content.ContextWrapper;
import android.view.LayoutInflater;

import mortar.Blueprint;

public class ScopedContext extends ContextWrapper {

    private LayoutInflater inflater;
    private Blueprint screen;

    public ScopedContext(Context context, Blueprint screen) {
        super(context);
        this.screen = screen;
    }

    @Override
    public Object getSystemService(String name) {
        if(LAYOUT_INFLATER_SERVICE.equals(name)) {
            if (inflater == null) {
                inflater = LayoutInflater.from(getBaseContext()).cloneInContext(this);
            }
            return inflater;
        }




        return super.getSystemService(name);
    }
} // end ScopedContext
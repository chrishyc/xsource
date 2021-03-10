package com.xsource;

/*
 * Copyright 2001-2005 The Apache Software Foundation.
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

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;

@Mojo(name = "chris", defaultPhase = LifecyclePhase.COMPILE)
public class MyMojo
        extends AbstractMojo {
    /**
     * Location of the file.
     *
     * @parameter expression="${project.build.directory}"
     * @required
     */
    @Parameter( defaultValue = "${project.build.directory}", required = true )
    private File outputDirectory;
    
    @Parameter
    private String fruit;
    
    @Parameter
    private String book;
    
    @Parameter
    private String hobby;
    
    public void execute()
            throws MojoExecutionException {
        System.out.println("==================hi,i am chris==============:\n" +
                "project.build.directory:" + outputDirectory + "\n" +
                "fruit:" + fruit + "\n" +
                "book:" + book + "\n" +
                "hobby:" + hobby + "\n");
    }
}

package com.fb.hckr.installer;

import java.util.*;

/**
 * Date: 1/31/13
 * Contains the current state of the installer
 * mail to: amodi@salesforce.com
 */
public class InstallerState {

    /**
     * Contains all the installed programs. Since a program is unique we use a set across.
     */
    private Set<String> installedPrograms;

    /**
     * Need to track a program if its explicitly installed, since the recursion cannot differentiate between the
     * programs
     */
    private Map<String, Boolean> firstClassInstall;
    /**
     * Contains the dependency map of a program as specified by the DependCommand
     */
    private Map<String, HashSet<String>> dependencyMap;
    /**
     * Reverse map of a program to all its installed dependent programs. This is used
     * to check that a program can only be removed if there are no dependencies.
     */
    private Map<String, HashSet<String>> reverseDepMapOfInstalledPrograms;

    public InstallerState(){
        installedPrograms = new HashSet<String>();
        dependencyMap = new HashMap<String, HashSet<String>>();
        reverseDepMapOfInstalledPrograms = new HashMap<String, HashSet<String>>();
    }

    public void updateDependency(String program, HashSet<String> dependencies){
        dependencyMap.put(program,dependencies);
    }


    public void installProgramFirst(String program){
        firstClassInstall.put(program, true);
        installProgram(program);
    }

    public void installProgram(String program){
        if(installedPrograms.contains(program)){
            System.out.println("\t " + program + " is already installed");
        }else{
            installedPrograms.add(program);
            Set<String> dependencies = dependencyMap.get(program);
            if(dependencies != null){
                for(String dependency : dependencies){
                    installProgram(dependency);
                    updateReverseDeps(dependency,program);
                }
            }
            System.out.println("\t Installing " + program);
        }
    }

    public void removeProgram(String program){
        if(!installedPrograms.contains(program)){
            System.out.println("\t " + program + " is not installed.");
        }else{
            Set<String> reverseDeps = reverseDepMapOfInstalledPrograms.get(program);
            if(reverseDeps == null || reverseDeps.size() ==0){
                System.out.println("\t Removing " + program);
                installedPrograms.remove(program);
                updateReverseDependencyMap(program);
            }else{
                System.out.println("\t" + program + " is still needed");
                return;
            }
        }
    }

    private void updateReverseDependencyMap(String program){
        Set<String> dependencies = dependencyMap.get(program);
        if(dependencies != null && dependencies.size() > 0){
            for(String dependency : dependencies){
                Set<String> programsDependOn = reverseDepMapOfInstalledPrograms.get(dependency);
                if(programsDependOn != null && programsDependOn.contains(program)){
                    programsDependOn.remove(program);
                    if(programsDependOn.size()==0){
                        removeProgram(dependency);
                    }
                }
            }
        }
    }

    private void updateReverseDeps(String dependency, String program){
        HashSet<String> reverseDeps = reverseDepMapOfInstalledPrograms.get(dependency);
        if(reverseDeps == null){
            reverseDeps = new HashSet<String>();
            reverseDepMapOfInstalledPrograms.put(dependency,reverseDeps);
        }
        if(!reverseDeps.contains(program)){
            reverseDeps.add(program);
        }
    }

    public void listInstalledPrograms(){
        for(String program : installedPrograms){
            System.out.println("\t" + program);
        }
    }

}

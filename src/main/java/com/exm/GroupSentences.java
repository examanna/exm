package com.exm;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Anna Kuranda on 6/16/2017.
 */
public class GroupSentences {
    public Map<Integer, Map<String, String>> groupText(List<String> lines){
        if(lines == null || lines.isEmpty()) return null;
        Map<Integer,Map<String,String>> linesGroup = Maps.newHashMap();
        int groupNum = 0;
        while(!lines.isEmpty() ){
            groupNum++;
            updateGroup(groupNum,lines,linesGroup);


        }
        return linesGroup;
    }

    private void updateGroup(int groupNum, List<String> lines, Map<Integer,Map<String,String>> linesGroup) {
        List<String> notMatched = Lists.newArrayList();
        //get line without date at the start

        try {
            String mainLine = lines.remove(0);

            Set<String> mainLineWords  = line2words(mainLine);
            if(!mainLineWords.isEmpty())
                lines.forEach(l->{
                    if(mainLine.trim().equals(l.trim()))
                    {
                        System.out.println("Found two identical lines " +mainLine );
                    }
                    else {
                        Set<String> words = line2words(l);
                        if (words.size() != mainLineWords.size())
                            notMatched.add(l);
                        else {

                            Set<String> notMatchWord = Sets.newHashSet();

                            for (String w : words) {
                                if (!mainLineWords.contains(w)) notMatchWord.add(w);
                                if (notMatchWord.size() > 1) break;
                            }

                            //there are more than one differ word -- sentences not from same group
                            if (notMatchWord.size() > 1) {
                                notMatched.add(l);
                            } else {
                                //if first run add differ word from main sentence
                                if (!linesGroup.containsKey(groupNum)) {
                                    Map<String, String> belongGroup = Maps.newHashMap();
                                    mainLineWords.forEach(w -> {
                                        if (!words.contains(w)) {
                                            belongGroup.put(mainLine, w);
                                            linesGroup.put(groupNum, belongGroup);

                                        }

                                    });
                                }
                                //add to group sentence
                                linesGroup.get(groupNum).put(l, notMatchWord.iterator().next());
                            }

                        }
                    }
                });


        }catch (Exception e){
            e.printStackTrace();
        }
        lines = notMatched;
    }

    private Set<String> line2words(String line){
        line = line.substring(20);
        Set<String> words = Sets.newHashSet();
        try {
            Collections.addAll(words, line.split("\\s+"));
        }catch(Exception e){
            e.printStackTrace();
        }
        return words;

    }

    public static void main(String[] args){
        FileUtil fileUtil = new FileUtil();
        List<String> lines = fileUtil.readFileFromClasspath("log_text.txt");
        GroupSentences groupSentences = new GroupSentences();
        Map<Integer,Map<String,String>> groups = groupSentences.groupText(lines);

        System.out.println(groupSentences.toString(groups)  )      ;
    }

    private String toString(Map<Integer, Map<String, String>> groups) {
        StringBuilder stringBuilder = new StringBuilder();
        groups.keySet().forEach(group->{
            StringBuilder sb = new StringBuilder();
            sb.append("The changing words was :")     ;
            Map<String,String> lines = groups.get(group);
            lines.keySet().forEach(l->{
                stringBuilder.append(l).append("\n");
                sb.append(lines.get(l)).append(", ");

            });
            String chngWords = sb.toString();
            if(chngWords.endsWith(", "))
            {
                chngWords = chngWords.trim().substring(0, chngWords.length() - 2);

            }
            stringBuilder.append(chngWords).append("\n");

        });

       return stringBuilder.toString();

    }

}

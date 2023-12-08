import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day05 {
    public static Map<String, List<List<Long>>> almanacMap; 
    public static void main(String[] args) {
        List<String> allLines;
        try{
            allLines = Files.readAllLines(Paths.get("input.txt"));
        } catch(IOException e) {
            e.printStackTrace();
            return;
        }
        long[] seeds = parseSeeds(allLines.get(0));
        List<List<String>> stringAlmanac = almanacStrings(allLines.subList(2, allLines.size())); 

        almanacMap = parseMap(stringAlmanac);
        String[] guides = {"seed-to-soil", 
                            "soil-to-fertilizer",
                            "fertilizer-to-water",
                            "water-to-light",
                            "light-to-temperature",
                            "temperature-to-humidity",
                            "humidity-to-location"};
        // PART ONE                    
        // for (var i=0; i<guides.length; i++) {
        //     seeds = findSoils(guides[i], seeds); 
        // }                    
        // long answer = seeds[0]; 
        // for (var i=0; i<seeds.length; i++){
        //     if(seeds[i] < answer) {
        //         answer = seeds[i];
        //     }
        // }    
        // System.out.println(answer);

        // PART TWO
        List<long[]> seedList = new ArrayList<>();
        for (var i=0; i<seeds.length-1; i+=2){
            long[] seedRange = {seeds[i], (seeds[i]+seeds[i+1]-1)};
            seedList.add(seedRange); 
        }

        for (var i =0; i<guides.length; i++) {
            seedList = solvePart2(guides[i], seedList);
        }
        seedList.sort((v1,v2) -> Long.valueOf(v1[0]).compareTo(Long.valueOf(v2[0])));

        long[] lowest = seedList.get(0); 
        System.out.println(lowest[0]);
    }

    public static long[] parseSeeds(String firstLine) {
        var subs = firstLine.split("\\s");
        long[] seeds = new long[subs.length-1]; 
        for(var i=0; i<seeds.length; i++) {
            seeds[i] = Long.parseLong(subs[i+1]); 
        }
        return seeds; 
    }

    public static List<List<String>> almanacStrings(List<String> lines) {
        List<List<String>> almanac = new ArrayList<>(); 
        var start = 0;
        var end = 0; 
        for (var i=0; i<lines.size(); i++) {
             
            if (!lines.get(i).isEmpty()){
                end++; 
            } 
            if (lines.get(i).isEmpty() || i == lines.size()-1) {
                almanac.add(lines.subList(start, end));
                start = end+1;
                end = start;
            }  

        }
        return almanac;
    }

    public static Map<String, List<List<Long>>> parseMap(List<List<String>> stringAlmanac) {
        Map<String, List<List<Long>>> theMap = new HashMap<>(); 
        for (var list : stringAlmanac) {
            String key = list.get(0).replace(" map:", "");
            List<List<Long>> listoflists = new ArrayList<>();  
            for (var i=1; i<list.size(); i++) {
                List<Long> longList = new ArrayList<>(); 
                var subStrings = list.get(i).split("\\s");
                for (var str : subStrings) {
                    str.replaceAll("\\s*", "");
                    if(!str.isEmpty()) {
                        longList.add(Long.parseLong(str));
                    } 
                }
                listoflists.add(longList);
            }
            theMap.put(key, listoflists);
        }
        return theMap; 
    }

    public static long[] findSoils(String key, long[] seeds) {
        long[] soils = new long[seeds.length];
                  
            var soilsForSeeds = almanacMap.get(key);
            Boolean inRange = false; 
            for(var i=0; i<seeds.length; i++) {
                for(var sFs : soilsForSeeds) {
                    if(seeds[i] >= sFs.get(1) && seeds[i] < sFs.get(1) + sFs.get(2)) {
                        soils[i] = sFs.get(0) + (seeds[i]-sFs.get(1));
                        inRange = true;  
                    }
                }
                if(!inRange) {
                        soils[i] = seeds[i];
                }
                inRange = false; 
            }
        return soils; 
    }

    public static List<long[]> solvePart2(String key, List<long[]> remaining) {
        var soilsForSeeds = almanacMap.get(key);
        List<long[]> result = new ArrayList<>();
        List<long[]> newRanges = new ArrayList<>(); 

        // for each seed rangesss
        for (var sFs: soilsForSeeds){

            List<long[]> toProcess = new ArrayList<>(remaining);

            // for each soil guide range, calculate soil range for each seed range
            for(var seedRange : toProcess) {
                long[] sourceRange = {sFs.get(1), sFs.get(1)+sFs.get(2)-1};
                long[] destRange = {sFs.get(0), sFs.get(0)+sFs.get(2)-1};
                // If the seed starts in the soil range and seed range ends in the soil range
                // add the destination range to newRanges, remove sourceRange from remaining
                if (seedRange[0] >= sourceRange[0] && seedRange[1] <= sourceRange[1]) {
                    long[] newSoilRange = {(destRange[0]+(seedRange[0]-sourceRange[0])), 
                                           (destRange[0]+(seedRange[1]-sourceRange[0]))};  
                    newRanges.add(newSoilRange);
                    remaining.remove(seedRange);
                }
                // If the seed range starts in the soil range and then extends beyond the soil range
                if (seedRange[0] >= sourceRange[0] && seedRange[0] <= sourceRange[1] && seedRange[1] > sourceRange[1]) {
                    // get new soil range for the part inside the seed source range
                    long[] newStartsInsideSoilRange = {(destRange[0])+(seedRange[0]-sourceRange[0]), destRange[1]};
                    
                    // seed range outside the seed source range, could be just the same soil range, OR
                    long[] partOutsideSoilRange = {(sourceRange[1]+1), seedRange[1]};
                    newRanges.add(newStartsInsideSoilRange);   
                    remaining.add(partOutsideSoilRange);
                    remaining.remove(seedRange);
                }
                
                // If the seed range starts outside the soil range and ends inside the soil range
                if (seedRange[0] < sourceRange[0] && seedRange[1] >= sourceRange[0] && seedRange[1] <= sourceRange[1]) {
                    long[] startsOutsideSoilRange = {seedRange[0], sourceRange[0]-1};
                    long[] partInsideSoilRange = {destRange[0], (destRange[0]+seedRange[1]-sourceRange[0])}; 
                    newRanges.add(partInsideSoilRange);
                    remaining.add(startsOutsideSoilRange);
                    remaining.remove(seedRange);
                    
                }
                // If the seed range begins outside the soil range, spans the entire range, and ends outside the range
                if (seedRange[0] < sourceRange[0] && seedRange[1] > sourceRange[1]) {
                    long[] partBefore = {seedRange[0], sourceRange[0]-1};
                    long[] partAfter = {sourceRange[1]+1, seedRange[1]};
                    long[] partInside = {destRange[0], destRange[1]};
                    newRanges.add(partInside);
                    remaining.add(partBefore);
                    remaining.add(partAfter);
                    remaining.remove(seedRange);
                }
                // If the seed range ends before the soil range begins OR if the seed range begins after the soil range ends
                // In this case it does not get removed from the remaining list
            }  

        }
        result.addAll(newRanges); 
        result.addAll(remaining); 
        return result; 
    }
}
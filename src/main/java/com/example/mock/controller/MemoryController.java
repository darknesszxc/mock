package com.example.mock.controller;
import org.springframework.web.bind.annotation.*;
        import java.util.*;

@RestController
public class MemoryController {

    private static final List<byte[]> memoryLeak = new ArrayList<>();

    @GetMapping("/leak")
    public String leakMemory(@RequestParam(defaultValue = "10") int mb) {
        for (int i = 0; i < mb; i++) {
            memoryLeak.add(new byte[1024 * 1024]); // 1 MB
        }
        return "Allocated " + mb + " MB. Total allocated: " + memoryLeak.size() + " MB";
    }

    @GetMapping("/reset-leak")
    public String clearMemory() {
        memoryLeak.clear();
        System.gc();
        return "Memory cleared";
    }
}
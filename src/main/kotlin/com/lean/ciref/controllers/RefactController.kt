package com.lean.ciref.controllers

import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken
import com.lean.ciref.DTO.DetectAllReq
import com.lean.ciref.DTO.DetectBetweenCommits
import com.lean.ciref.DTO.DetectCommitReq
import com.lean.ciref.entities.Refact
import org.eclipse.jgit.util.FileUtils
import org.refactoringminer.api.GitHistoryRefactoringMiner
import org.refactoringminer.api.GitService
import org.refactoringminer.api.Refactoring
import org.refactoringminer.api.RefactoringHandler
import org.refactoringminer.rm1.GitHistoryRefactoringMinerImpl
import org.refactoringminer.util.GitServiceImpl
import org.springframework.web.bind.annotation.*
import java.nio.file.Paths
import java.util.*


@RestController
class RefactController {

    @CrossOrigin()
    @RequestMapping("refact/all")
    @PostMapping
    @ResponseBody
    fun listRefacts(@RequestBody request: DetectAllReq): Array<Refact> {
        val gitService: GitService = GitServiceImpl()
        val miner: GitHistoryRefactoringMiner = GitHistoryRefactoringMinerImpl()

        val repo: org.eclipse.jgit.lib.Repository = gitService.cloneIfNotExists(
            "tmp/" + request.name,
            request.url
        )
        val gson = Gson()
        val arrayTutorialType = object : TypeToken<Array<Refact>>() {}.type

        var refacts = mutableListOf<String>();
        miner.detectAll(repo, request.branch, object : RefactoringHandler() {
            override fun handle(commitId: String, refactorings: List<Refactoring?>) {
                println("Refactorings at $commitId")
                for (ref in refactorings) {
                    if (ref != null) {
                        val input = ref.toJSON()
                        val serialized = input.toByteArray(charset("UTF8"))
                        val yourId = UUID.nameUUIDFromBytes(serialized)

                        val gson = Gson()
                        val jsonElement = gson.fromJson(ref.toJSON(), JsonElement::class.java).asJsonObject
                        jsonElement.addProperty("id", yourId.toString())
                        jsonElement.addProperty("commitId", commitId)
                        refacts.add(jsonElement.toString())
                    }
                }
            }
        });
        repo.close()
        var tutorials: Array<Refact> = gson.fromJson(refacts.toString(), arrayTutorialType)
        val dir = Paths.get ("./tmp/" + request.name);

        FileUtils.delete(dir.toFile(),1);
        return tutorials
    }

    @CrossOrigin()
    @RequestMapping("refact/between-commits")
    @PostMapping
    @ResponseBody
    fun listRefactsBetweenCommits(@RequestBody request: DetectBetweenCommits): Array<Refact> {
        val gitService: GitService = GitServiceImpl()
        val miner: GitHistoryRefactoringMiner = GitHistoryRefactoringMinerImpl()

        val repo: org.eclipse.jgit.lib.Repository = gitService.cloneIfNotExists(
            "tmp/" + request.name,
            request.url
        )
        val gson = Gson()
        val arrayTutorialType = object : TypeToken<Array<Refact>>() {}.type

        var refacts = mutableListOf<String>();
        miner.detectBetweenCommits(repo, request.initialCommit,request.endCommit, object : RefactoringHandler() {
            override fun handle(commitId: String, refactorings: List<Refactoring?>) {
                println("Refactorings at $commitId")
                for (ref in refactorings) {
                    if (ref != null) {
                        val input = ref.toJSON()
                        val serialized = input.toByteArray(charset("UTF8"))
                        val yourId = UUID.nameUUIDFromBytes(serialized)

                        val gson = Gson()
                        val jsonElement = gson.fromJson(ref.toJSON(), JsonElement::class.java).asJsonObject
                        jsonElement.addProperty("id", yourId.toString())
                        jsonElement.addProperty("commitId", commitId)
                        refacts.add(jsonElement.toString())
                    }
                }
            }
        })
        var tutorials: Array<Refact> = gson.fromJson(refacts.toString(), arrayTutorialType)

        return tutorials
    }

    @CrossOrigin()
    @RequestMapping("refact/commit")
    @PostMapping
    @ResponseBody
    fun listRefactsCommit(@RequestBody request: DetectCommitReq): Array<Refact> {
        val gitService: GitService = GitServiceImpl()
        val miner: GitHistoryRefactoringMiner = GitHistoryRefactoringMinerImpl()

        val repo: org.eclipse.jgit.lib.Repository = gitService.cloneIfNotExists(
            "tmp/" + request.name,
            request.url
        )
        val gson = Gson()
        val arrayTutorialType = object : TypeToken<Array<Refact>>() {}.type

        var refacts = mutableListOf<String>();
        miner.detectAtCommit(repo,request.commit, object : RefactoringHandler() {
            override fun handle(commitId: String, refactorings: List<Refactoring?>) {
                println("Refactorings at $commitId")
                for (ref in refactorings) {
                    if (ref != null) {
                        val input = ref.toJSON()
                        val serialized = input.toByteArray(charset("UTF8"))
                        val yourId = UUID.nameUUIDFromBytes(serialized)

                        val gson = Gson()
                        val jsonElement = gson.fromJson(ref.toJSON(), JsonElement::class.java).asJsonObject
                        jsonElement.addProperty("id", yourId.toString())
                        jsonElement.addProperty("commitId", commitId)
                        refacts.add(jsonElement.toString())
                    }
                }
            }
        })
        var tutorials: Array<Refact> = gson.fromJson(refacts.toString(), arrayTutorialType)

        return tutorials
    }
}
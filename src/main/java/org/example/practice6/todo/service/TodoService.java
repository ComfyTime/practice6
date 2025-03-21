package org.example.practice6.todo.service;

import lombok.RequiredArgsConstructor;
import org.example.practice6.member.entity.Member;
import org.example.practice6.member.repository.MemberRepository;
import org.example.practice6.todo.dto.*;
import org.example.practice6.todo.entity.Todo;
import org.example.practice6.todo.repository.TodoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public TodoSaveResponseDto save(Long memberId, TodoSaveRequestDto dto) {

        Member member = memberRepository.findById((memberId)).orElseThrow(
                () -> new IllegalStateException("해당 없음")
        );

        Todo todo = new Todo(
                dto.getContent(),
                member
        );
        Todo savedTodo = todoRepository.save(todo);
        return new TodoSaveResponseDto(
                savedTodo.getId(),
                savedTodo.getContent(),
                member.getId(),
                member.getEmail()
        );
    }

    @Transactional(readOnly = true)
    public List<TodoResponseDto> findAll() {
        List<Todo> todos = todoRepository.findAll();
        List<TodoResponseDto> dtos = new ArrayList<>();
        for (Todo todo : todos) {
            dtos.add(new TodoResponseDto(
                    todo.getId(),
                    todo.getContent()
            ));
        }
        return dtos;
    }

    @Transactional(readOnly = true)
    public TodoResponseDto findById(Long todoId) {
        Todo todo = todoRepository.findById(todoId).orElseThrow(
                () -> new IllegalStateException("해당 없음")
        );
        return new TodoResponseDto(
                todo.getId(),
                todo.getContent()
        );
    }

    public TodoUpdateResponseDto update(Long memberId, Long todoId, TodoUpdateRequestDto dto) {

        Member member = memberRepository.findById((memberId)).orElseThrow(
                () -> new IllegalStateException("해당 없음")
        );

        Todo todo = todoRepository.findById(todoId).orElseThrow(
                () -> new IllegalStateException("해당 없음")
        );

        if (!todo.getMember().getId().equals(member.getId())) {
            throw new IllegalStateException("권한 없음");
        }

        todo.update(dto.getContent());
        return new TodoUpdateResponseDto(
                todo.getId(),
                todo.getContent()
        );
    }

    public void deleteById(Long memberId, Long todoId) {
        Member member = memberRepository.findById((memberId)).orElseThrow(
                () -> new IllegalStateException("해당 없음")
        );

        Todo todo = todoRepository.findById(todoId).orElseThrow(
                () -> new IllegalStateException("해당 없음")
        );

        if (!todo.getMember().getId().equals(member.getId())) {
            throw new IllegalStateException("권한 없음");
        }

        todoRepository.deleteById(todoId);
    }
}
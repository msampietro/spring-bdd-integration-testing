package com.msampietro.springbddintegrationtesting.module.base.hal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.util.MultiValueMap;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class PageModelWrapper<T> extends PagedModel<T> {

    private static final String PAGE_PARAM = "page";
    private static final String SIZE_PARAM = "size";
    private final Page<T> page;
    private final Pageable pageable;
    private MultiValueMap<String, String> queryParams;
    private final Map<String, Iterable<T>> embeddedContent = new HashMap<>();
    private final long totalElements;
    private final long totalPages;

    public PageModelWrapper(String relation, Page<T> page, Pageable pageable, MultiValueMap<String, String> queryParams) {
        super();
        this.page = page;
        this.queryParams = queryParams;
        this.pageable = pageable;
        this.buildPageLinks();
        this.embeddedContent.put(relation, page.getContent());
        this.totalElements = page.getTotalElements();
        this.totalPages = page.getTotalPages();
    }

    public PageModelWrapper(Page<T> page, Pageable pageable) {
        super();
        this.page = page;
        this.pageable = pageable;
        this.buildPageLinks();
        this.embeddedContent.put("content", page.getContent());
        this.totalElements = page.getTotalElements();
        this.totalPages = page.getTotalPages();
    }

    private void buildPageLinks() {
        if (!this.getLinks().isEmpty())
            this.removeLinks();
        addFirstPageLink();
        addPreviousLink();
        addNextLink();
        addLastPageLink();
        addSelfPageLink();
    }

    private void addPreviousLink() {
        if (page.hasPrevious())
            this.add(buildPageLink(pageable.getPageNumber() - 1, pageable.getPageSize(), IanaLinkRelations.PREV.value()));
    }

    private void addNextLink() {
        if (page.hasNext())
            this.add(buildPageLink(pageable.getPageNumber() + 1, pageable.getPageSize(), IanaLinkRelations.NEXT.value()));
    }

    private void addFirstPageLink() {
        this.add(buildPageLink(pageable.first().getPageNumber() + 1, pageable.getPageSize(), IanaLinkRelations.FIRST.value()));
    }

    private void addLastPageLink() {
        this.add(buildPageLink(page.getTotalPages() == 0 ? 1 : page.getTotalPages(), pageable.getPageSize(), IanaLinkRelations.LAST.value()));
    }

    private void addSelfPageLink() {
        this.add(buildPageLink(pageable.getPageNumber(), pageable.getPageSize(), IanaLinkRelations.SELF.value()));
    }

    private Link buildPageLink(int page, int size, String rel) {
        String path = createBuilder()
                .queryParam(PAGE_PARAM, page)
                .queryParam(SIZE_PARAM, size)
                .queryParams(queryParams)
                .build()
                .toUriString();
        return Link.of(path, rel);
    }

    private ServletUriComponentsBuilder createBuilder() {
        return ServletUriComponentsBuilder.fromCurrentRequestUri();
    }

    @JsonProperty("_embedded")
    public Map<String, Iterable<T>> getEmbeddedContent() {
        return this.embeddedContent;
    }

    @SuppressWarnings("NullableProblems")
    @JsonIgnore
    @Override
    public Collection<T> getContent() {
        return super.getContent();
    }

    public long getTotalElements() {
        return this.totalElements;
    }

    public long getTotalPages() {
        return this.totalPages;
    }

}

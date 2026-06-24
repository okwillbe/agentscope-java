/*
 * Copyright 2024-2026 the original author or authors.
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
package io.agentscope.core.rag.store.dto;

import org.jspecify.annotations.Nullable;

/**
 * {@summary Search document data transfer object, used to encapsulate parameters for vector search. (Search document data transfer object, used to encapsulate parameters for vector search.)}
 * Search document data transfer object, used to encapsulate parameters for vector search.
 * Contains vector name, query embedding, limit for returned results, and similarity score threshold.
 */
public class SearchDocumentDto {

    /**
     * {@summary Search document data transfer object, used to encapsulate parameters for vector search. (Search document data transfer object, used to encapsulate parameters for vector search.)}
     * Vector name, used to specify which vector space to search in.
     */
    @Nullable private String vectorName;

    /**
     * {@summary Search document data transfer object, used to encapsulate parameters for vector search. (Search document data transfer object, used to encapsulate parameters for vector search.)}
     * Query embedding vector, used to search for similar vectors in the vector space.
     */
    private double[] queryEmbedding;

    /**
     * {@summary Search document data transfer object, used to encapsulate parameters for vector search. (Search document data transfer object, used to encapsulate parameters for vector search.)}
     * Limit for the number of returned results.
     */
    private int limit;

    /**
     * {@summary Search document data transfer object, used to encapsulate parameters for vector search. (Search document data transfer object, used to encapsulate parameters for vector search.)}
     * Similarity score threshold, used to filter search results,
     * only results with scores above this threshold will be returned.
     */
    @Nullable private Double scoreThreshold;

    /**
     * {@summary Search document data transfer object, used to encapsulate parameters for vector search. (Search document data transfer object, used to encapsulate parameters for vector search.)}
     * Private constructor for builder class.
     *
     * @param vectorName     Vector name
     * @param queryEmbedding Query embedding vector
     * @param limit          Limit for returned results
     * @param scoreThreshold Similarity score threshold
     */
    private SearchDocumentDto(
            String vectorName, double[] queryEmbedding, int limit, Double scoreThreshold) {
        this.vectorName = vectorName;
        this.queryEmbedding = queryEmbedding;
        this.limit = limit;
        this.scoreThreshold = scoreThreshold;
    }

    /**
     * {@summary Search document data transfer object, used to encapsulate parameters for vector search. (Search document data transfer object, used to encapsulate parameters for vector search.)}
     * Get the vector name.
     *
     * @return Vector name
     */
    public String getVectorName() {
        return vectorName;
    }

    /**
     * {@summary Search document data transfer object, used to encapsulate parameters for vector search. (Search document data transfer object, used to encapsulate parameters for vector search.)}
     * Set the vector name.
     *
     * @param vectorName Vector name
     */
    public void setVectorName(String vectorName) {
        this.vectorName = vectorName;
    }

    /**
     * {@summary Search document data transfer object, used to encapsulate parameters for vector search. (Search document data transfer object, used to encapsulate parameters for vector search.)}
     * Get the query embedding vector.
     *
     * @return Query embedding vector
     */
    public double[] getQueryEmbedding() {
        return queryEmbedding;
    }

    /**
     * {@summary Search document data transfer object, used to encapsulate parameters for vector search. (Search document data transfer object, used to encapsulate parameters for vector search.)}
     * Set the query embedding vector.
     *
     * @param queryEmbedding Query embedding vector
     */
    public void setQueryEmbedding(double[] queryEmbedding) {
        this.queryEmbedding = queryEmbedding;
    }

    /**
     * {@summary Search document data transfer object, used to encapsulate parameters for vector search. (Search document data transfer object, used to encapsulate parameters for vector search.)}
     * Get the limit for returned results.
     *
     * @return Limit for returned results
     */
    public int getLimit() {
        return limit;
    }

    /**
     * {@summary Search document data transfer object, used to encapsulate parameters for vector search. (Search document data transfer object, used to encapsulate parameters for vector search.)}
     * Set the limit for returned results.
     *
     * @param limit Limit for returned results
     */
    public void setLimit(int limit) {
        this.limit = limit;
    }

    /**
     * {@summary Search document data transfer object, used to encapsulate parameters for vector search. (Search document data transfer object, used to encapsulate parameters for vector search.)}
     * Get the similarity score threshold.
     *
     * @return Similarity score threshold
     */
    public Double getScoreThreshold() {
        return scoreThreshold;
    }

    /**
     * {@summary Search document data transfer object, used to encapsulate parameters for vector search. (Search document data transfer object, used to encapsulate parameters for vector search.)}
     * Set the similarity score threshold.
     *
     * @param scoreThreshold Similarity score threshold
     */
    public void setScoreThreshold(Double scoreThreshold) {
        this.scoreThreshold = scoreThreshold;
    }

    @Override
    public String toString() {
        return String.format(
                "SearchDocumentDto(vectorName=%s, limit=%s, scoreThreshold=%s)",
                this.vectorName != null ? this.vectorName : "null",
                this.limit,
                this.scoreThreshold != null ? String.format("%.3f", this.scoreThreshold) : "null");
    }

    /**
     * {@summary Search document data transfer object, used to encapsulate parameters for vector search. (Search document data transfer object, used to encapsulate parameters for vector search.)}
     * Create a SearchDocumentDto Builder instance.
     *
     * @return Builder instance
     */
    public static SearchDocumentDtoBuilder builder() {
        return new SearchDocumentDtoBuilder();
    }

    /**
     * {@summary Search document data transfer object, used to encapsulate parameters for vector search. (Search document data transfer object, used to encapsulate parameters for vector search.)}
     * Builder class for SearchDocument DTO.
     */
    public static class SearchDocumentDtoBuilder {
        private String vectorName;
        private double[] queryEmbedding;
        private int limit;
        private Double scoreThreshold;

        private SearchDocumentDtoBuilder() {}

        /**
         * {@summary Search document data transfer object, used to encapsulate parameters for vector search. (Search document data transfer object, used to encapsulate parameters for vector search.)}
         * Set the vector name.
         *
         * @param vectorName Vector name
         * @return Builder instance
         */
        public SearchDocumentDtoBuilder vectorName(String vectorName) {
            this.vectorName = vectorName;
            return this;
        }

        /**
         * {@summary Search document data transfer object, used to encapsulate parameters for vector search. (Search document data transfer object, used to encapsulate parameters for vector search.)}
         * Set the query embedding vector.
         *
         * @param queryEmbedding Query embedding vector
         * @return Builder instance
         */
        public SearchDocumentDtoBuilder queryEmbedding(double[] queryEmbedding) {
            this.queryEmbedding = queryEmbedding;
            return this;
        }

        /**
         * {@summary Search document data transfer object, used to encapsulate parameters for vector search. (Search document data transfer object, used to encapsulate parameters for vector search.)}
         * Set the limit for returned results.
         *
         * @param limit Limit for returned results
         * @return Builder instance
         */
        public SearchDocumentDtoBuilder limit(int limit) {
            this.limit = limit;
            return this;
        }

        /**
         * {@summary Search document data transfer object, used to encapsulate parameters for vector search. (Search document data transfer object, used to encapsulate parameters for vector search.)}
         * Set the similarity score threshold.
         *
         * @param scoreThreshold Similarity score threshold
         * @return Builder instance
         */
        public SearchDocumentDtoBuilder scoreThreshold(Double scoreThreshold) {
            this.scoreThreshold = scoreThreshold;
            return this;
        }

        /**
         * {@summary Search document data transfer object, used to encapsulate parameters for vector search. (Search document data transfer object, used to encapsulate parameters for vector search.)}
         * Build a SearchDocumentDto instance.
         *
         * @return Search document DTO instance
         */
        public SearchDocumentDto build() {
            return new SearchDocumentDto(
                    this.vectorName, this.queryEmbedding, this.limit, this.scoreThreshold);
        }
    }
}

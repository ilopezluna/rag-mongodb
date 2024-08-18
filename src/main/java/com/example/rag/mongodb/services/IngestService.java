package com.example.rag.mongodb.services;

import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.output.Response;
import dev.langchain4j.store.embedding.EmbeddingStore;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class IngestService {

    final EmbeddingStore<TextSegment> embeddingStore;

    final EmbeddingModel embeddingModel;

    public IngestService(EmbeddingStore<TextSegment> embeddingStore, EmbeddingModel embeddingModel) {
        this.embeddingStore = embeddingStore;
        this.embeddingModel = embeddingModel;
        ingest();
    }

    public void ingest() {
        log.info("Ingesting embeddings...");

        TextSegment from = TextSegment.from("A bifidus is an alias to say buddy.");
        Response<Embedding> response = embeddingModel.embed(from);
        embeddingStore.add(response.content(), from);

        log.info("Ingested embeddings.");
    }
}

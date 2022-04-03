package com.example.demo.core.news;

import com.example.demo.base.BaseRequest;
import com.example.demo.core.news.converter.NewsToNewsViewConverter;
import com.example.demo.core.news.web.NewsBaseRequest;
import com.example.demo.core.news.web.NewsView;
import org.aspectj.bridge.MessageUtil;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class NewsService {
    private final NewsRepository newsRepository;
    private final NewsToNewsViewConverter newsToNewsViewConverter;
    private final TeamRepository teamRepository;
    private final MessageUtil messageUtil;
    public  NewsService(NewsRepository newsRepository, NewsToNewsViewConverter newsToNewsViewConverter,
                        TeamRepository teamRepository, MessageUtil messageUtil){
        this.newsRepository = newsRepository;
        this.newsToNewsViewConverter=newsToNewsViewConverter;
        this.teamRepository = teamRepository;
        this.messageUtil = messageUtil;
    }

    public News findNewsOrThrow(Long id){
        return newsRepository.findById(id).orElseThrow(()-> new EntityNotFoundException(messageUtil.getMessage("news.NotFound", id)));
    }
    public NewsView getNews(Long id){
        News news = findNewsOrThrow(id);
        return newsToNewsViewConverter.convert(news );
    }
    public Page<NewsView> findAllNews(Pageable pageable){
        Page<News> news = newsRepository.findAll(pageable);
        List<NewsView> newsViews =new ArrayList<>();
        news.forEach(n->{
            NewsView newsView = newsToNewsViewConverter.convert(n);
            (newsViews).add(newsView);
        });
        return new PageImpl<>(newsViews, pageable, news.getTotalElements());
    }
    public NewsView create(NewsBaseRequest request){
        News news = new News();
        this.prepare(news, request);
        News newsSave = newsRepository.save(news);
        return newsToNewsViewConverter.convert(newsSave);
    }
    @Transactional
    public void delete(Long id){
        try {
            newsRepository.deleteById(id);
        }catch (EmptyResultDataAccessException e){
            throw new EntityNotFoundException(messageUtil.getMessage("news.NotFound", id));
        }
    }
    public NewsView update(News news, NewsBaseRequest request){
        News newNews = this.prepare(news,request);
        News newSave = newsRepository.save(newNews);
        return NewsToNewsViewConverter.convert(newsSave);
    }
    private News prepare(News news, NewsBaseRequest request){
        news.setName(request.getName());
        news.setContent(request.getContent());
        List<Team> teamList = teamRepository.findAllById(request.getTeams().stream().map(BaseRequest.Id::getId).collect(Collectors.toSet()));
        Set<Team> teams = new HashSet<>(teamList);
        news.setTeams(teams);
        return news;
    }
}

library(ggplot2)
library(dplyr)

pick <- function(condition){
  function(d) d %>% filter_(condition)
}

simulation_results = read.csv('result/result.csv',sep=";",header=TRUE)

plot <- ggplot(data = simulation_results, aes(y = Time..ms., x = Number.of.vertices, color = variable)) +
    xlab("Liczba wierzchołków") +
    ylab("Czas wykonania [ms]") +
    geom_point(data = pick(~Depth.of.tree == 126), colour = "red") +
    geom_point(data = pick(~Depth.of.tree == 206), colour = "green") + 
    geom_point(data = pick(~Depth.of.tree == 300), colour = "blue") + 
    geom_point(data = pick(~Depth.of.tree == 400), colour = "yellow") + 
    scale_fill_manual(labels = c("126", "206", "Nazwa3", "Nazwa4"), values = c("blue", "red", "yellow", "green"))

ggsave(paste("result/all", '.jpg', sep=""), plot = plot)

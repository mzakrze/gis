library("ggplot2")


theoretical_complexity <- function(depth) {
    function(verticesNo) {
        # FIXME - nie mam pojęcia czy taka faktycznie jest złożoność. Dałem narazie taką żeby sie ladnie rysowało
        verticesNo * verticesNo
    }
}

simulation_results = read.csv('result/result.csv',sep=";",header=TRUE)
simulation_results$Are.isomorphic <- simulation_results$Are.isomorphic == "true" # string => boolean

unique_depths = unique(simulation_results$Depth.of.tree)
for(depth in unique_depths) {
    depth_res = simulation_results[simulation_results$Depth.of.tree == depth, ]

    # map theoretical complexity to number of miliseconds
    # Big O-notation <=> (Exists c, n1) (For all n > n1): f(n) > c * g(n1)
    # c = ???
    real_time = depth_res$Time..ms.
    theory_time = sapply(depth_res$Number.of.vertices, theoretical_complexity(depth))
    c = mean(real_time / theory_time)

    plot <- ggplot(data = depth_res, aes(y = Time..ms., x = Number.of.vertices)) +
        ggtitle(paste("Complexity for depth =", toString(depth))) +
        xlab("Number of vertices") +
        ylab("Time [ms]") +
        geom_point() +
        stat_function(fun = function(n) { c  * theoretical_complexity(depth)(n) }, colour = "green")
    ggsave(paste("result/d_", toString(depth), '.jpg', sep=""), plot = plot)

}



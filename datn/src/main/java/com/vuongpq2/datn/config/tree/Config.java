package com.vuongpq2.datn.config.tree;
import com.fasterxml.jackson.annotation.JsonInclude;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class Config extends ConfigTree{
    private String container = "#collapsable-example";
    private  boolean animateOnInit = true;
    private Connectors connectors;
    private Node node;
    private Animation animation;
    private String scrollbar = "fancy"; // None
    public Config() {
        connectors = new Connectors();
        node = new Node();
        animation = new Animation();
    }

    public Connectors getConnectors() {
        return connectors;
    }

    public void setConnectors(Connectors connectors) {
        this.connectors = connectors;
    }

    public String getScrollbar() {
        return scrollbar;
    }

    public void setScrollbar(String scrollbar) {
        this.scrollbar = scrollbar;
    }

    public String getContainer() {
        return container;
    }

    public void setContainer(String container) {
        this.container = container;
    }

    public boolean isAnimateOnInit() {
        return animateOnInit;
    }

    public void setAnimateOnInit(boolean animateOnInit) {
        this.animateOnInit = animateOnInit;
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public Animation getAnimation() {
        return animation;
    }

    public void setAnimation(Animation animation) {
        this.animation = animation;
    }
}

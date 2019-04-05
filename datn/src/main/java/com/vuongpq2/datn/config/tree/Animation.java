package com.vuongpq2.datn.config.tree;
public class Animation {
    private String nodeAnimation = "easeOutBounce";
    private int nodeSpeed = 700;
    private String connectorsAnimation = "bounce";
    private int connectorsSpeed = 700;

    public Animation() {
    }

    public String getNodeAnimation() {
        return nodeAnimation;
    }

    public void setNodeAnimation(String nodeAnimation) {
        this.nodeAnimation = nodeAnimation;
    }

    public int getNodeSpeed() {
        return nodeSpeed;
    }

    public void setNodeSpeed(int nodeSpeed) {
        this.nodeSpeed = nodeSpeed;
    }

    public String getConnectorsAnimation() {
        return connectorsAnimation;
    }

    public void setConnectorsAnimation(String connectorsAnimation) {
        this.connectorsAnimation = connectorsAnimation;
    }

    public int getConnectorsSpeed() {
        return connectorsSpeed;
    }

    public void setConnectorsSpeed(int connectorsSpeed) {
        this.connectorsSpeed = connectorsSpeed;
    }
}

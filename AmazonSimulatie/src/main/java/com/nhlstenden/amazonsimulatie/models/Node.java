package com.nhlstenden.amazonsimulatie.models;

public class Node {
    private String id;
    private int x;
    private int z;
    private boolean isStellage;

    public Node(String id, int x, int z, boolean isStellage) {
        this.id = id;
        this.x = x;
        this.z = z;
        this.isStellage = isStellage;
    }

    public String getId() {
        return id;
    }

    public boolean isStellage() {
        return isStellage;
    }

    public int getX() {
        return x;
    }

    public int getZ() {
        return z;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Node other = (Node) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return id;
    }
}
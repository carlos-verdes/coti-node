package io.coti.cotinode.storage.Interfaces;

import io.coti.cotinode.data.Hash;
import io.coti.cotinode.data.IEntity;

import java.util.function.Function;

public interface IModel {
    void put(IEntity entity);
    void find(Function<IModel, Boolean> predicate);
    void getByHash(Hash hash);
}

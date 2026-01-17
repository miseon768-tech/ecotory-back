package com.example.ecotory.domain.KrwAsset.dto.response.KrwAsset;

import com.example.ecotory.domain.KrwAsset.entity.KrwAsset;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class KrwAssetByMemberResponse {
    private List<KrwAsset> KrwAssetList;
    private boolean success;
}

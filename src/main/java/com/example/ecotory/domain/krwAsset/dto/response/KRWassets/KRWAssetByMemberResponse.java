package com.example.ecotory.domain.krwAsset.dto.response.KRWassets;

import com.example.ecotory.domain.krwAsset.entity.krwAsset;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class KRWAssetByMemberResponse {
    private List<krwAsset> krwAssetList;
    private boolean success;
}

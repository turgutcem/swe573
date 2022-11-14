package com.swe573.swe573.model.enums;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class VoteTypeConverter implements AttributeConverter<VoteType,Integer> {
    @Override
    public Integer convertToDatabaseColumn(VoteType voteType) {
        if(voteType==null) return 0;
        switch (voteType){
            case UPVOTE:
                return 1;
            case DOWNVOTE:
                return -1;
            default:
                return 0;
        }

    }

    @Override
    public VoteType convertToEntityAttribute(Integer dbData) {
        if(dbData==null) return VoteType.NONVOTE;
        switch (dbData){
            case 1:
                return VoteType.UPVOTE;
            case -1:
                return VoteType.DOWNVOTE;
            default:
                return VoteType.NONVOTE;
        }
    }

}

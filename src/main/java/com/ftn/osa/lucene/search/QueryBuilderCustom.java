package com.ftn.osa.lucene.search;


import com.ftn.osa.util.SearchType;
import lombok.Getter;
import lombok.Setter;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

@Getter
@Setter
public class QueryBuilderCustom {

    private static int maxEdits = 1;

    public static QueryBuilder buildQuery(SearchType queryType, String field, String value) throws IllegalArgumentException {
        validateQueryFields(field, value);
        if(queryType.equals(SearchType.TERM)){
            return QueryBuilders.termQuery(field, value);
        } else if(queryType.equals(SearchType.MATCH)){
            return QueryBuilders.matchQuery(field, value);
        } else if(queryType.equals(SearchType.PHRASE)){
            return QueryBuilders.matchPhraseQuery(field, value);
        } else if(queryType.equals(SearchType.FUZZY)){
            return QueryBuilders.fuzzyQuery(field, value).fuzziness(Fuzziness.fromEdits(maxEdits));
        } else if(queryType.equals(SearchType.PREFIX)){
            return QueryBuilders.prefixQuery(field, value);
        } else if(queryType.equals(SearchType.RANGE)){
            String[] values = value.split("-");
            return QueryBuilders.rangeQuery(field).from(values[0]).to(values[1]);
        } else{
            return QueryBuilders.matchPhraseQuery(field, value);
        }
    }

    private static void validateQueryFields(String field, String value) {
        String errorMessage = "";
        if(field == null || field.equals("")){
            errorMessage += "Field not specified";
        }
        if(value == null){
            if(!errorMessage.equals("")) errorMessage += "\n";
            errorMessage += "Value not specified";
        }
        if(!errorMessage.equals("")){
            throw new IllegalArgumentException(errorMessage);
        }
    }

}

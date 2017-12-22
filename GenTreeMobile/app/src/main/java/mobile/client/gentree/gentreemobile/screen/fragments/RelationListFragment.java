package mobile.client.gentree.gentreemobile.screen.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import gentree.server.dto.RelationDTO;
import lombok.Getter;
import lombok.Setter;
import mobile.client.gentree.gentreemobile.R;
import mobile.client.gentree.gentreemobile.screen.adapters.RelationListAdapter;
import mobile.client.gentree.gentreemobile.screen.adapters.TabPageAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vanilka on 21/12/2017.
 */
public class RelationListFragment extends Fragment {

    private final ArrayList<RelationDTO> relations = new ArrayList<>();
    private ListView relationListView;

    @Getter
    @Setter
    private TabPageAdapter tabPageAdapter;

    public RelationListFragment() {}

    public static RelationListFragment newInstance(TabPageAdapter adapter) {
        RelationListFragment fragment = new RelationListFragment();
        fragment.setTabPageAdapter(adapter);
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.relation_list_fragment, container, false);
        relationListView = (ListView) view.findViewById(R.id.relationListView);
        ArrayAdapter<RelationDTO> adapter = new RelationListAdapter(relationListView.getContext(), relations);
        relationListView.setAdapter(adapter);
        return view;
    }

    public void addRelation(RelationDTO relation) {
        if (!relations.contains(relation)) relations.add(relation);
    }

    public void addRelations(List<RelationDTO> relationsToAdd) {
        relations.addAll(relationsToAdd);
    }

    public void setRelations(List<RelationDTO> relations) {
        this.relations.clear();
        this.relations.addAll(relations);
    }

    public void removeAllRelations() {
        relations.clear();
    }
    public void removeRelation(RelationDTO relation) {
        relations.remove(relation);
    }
}

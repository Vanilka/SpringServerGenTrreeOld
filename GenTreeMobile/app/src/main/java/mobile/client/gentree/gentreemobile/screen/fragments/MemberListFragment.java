package mobile.client.gentree.gentreemobile.screen.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import gentree.server.dto.MemberDTO;
import lombok.Getter;
import lombok.Setter;
import mobile.client.gentree.gentreemobile.R;
import mobile.client.gentree.gentreemobile.screen.adapters.MemberListAdapter;
import mobile.client.gentree.gentreemobile.screen.adapters.TabPageAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vanilka on 21/12/2017.
 */
public class MemberListFragment extends Fragment {

    private final ArrayList<MemberDTO> members = new ArrayList<>();
    private ListView memberListView;

    @Getter
    @Setter
    private TabPageAdapter tabPageAdapter;


    public MemberListFragment() {
    }

    public static MemberListFragment newInstance(TabPageAdapter tabPageAdapter) {
        MemberListFragment fragment = new MemberListFragment();
        fragment.setTabPageAdapter(tabPageAdapter);
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
        View view = inflater.inflate(R.layout.member_list_fragment, container, false);
        memberListView = (ListView) view.findViewById(R.id.memberListView);
        ArrayAdapter<MemberDTO> adapter = new MemberListAdapter(memberListView.getContext(), members);
        memberListView.setAdapter(adapter);
        return view;
    }

    public void addMember(MemberDTO member) {
        if (!members.contains(member)) members.add(member);
    }

    public void addMembers(List<MemberDTO> membersToAdd) {
        members.addAll(membersToAdd);
    }

    public void setMembers(List<MemberDTO> members) {
        this.members.clear();
        this.members.addAll(members);
    }

    public void removeAllMembers() {
        members.clear();
    }

    public void removeMember(MemberDTO member) {
        members.remove(member);
    }
}
